package com.ucapdm2025.taskspaces.data.repository.user

import android.content.Context
import android.net.Uri
import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.ucapdm2025.taskspaces.data.database.dao.UserDao
import com.ucapdm2025.taskspaces.data.database.entities.relational.toDomain
import com.ucapdm2025.taskspaces.data.database.entities.toDomain
import com.ucapdm2025.taskspaces.data.model.UserModel
import com.ucapdm2025.taskspaces.data.model.relational.toDatabase
import com.ucapdm2025.taskspaces.data.model.toDatabase
import com.ucapdm2025.taskspaces.data.remote.requests.SignUpRequest
import com.ucapdm2025.taskspaces.data.remote.responses.media.MediaResponse
import com.ucapdm2025.taskspaces.data.remote.responses.UserResponse
import com.ucapdm2025.taskspaces.data.remote.responses.toDomain
import com.ucapdm2025.taskspaces.data.remote.responses.toEntity
import com.ucapdm2025.taskspaces.data.remote.responses.workspace.toEntity
import com.ucapdm2025.taskspaces.data.remote.services.AuthService
import com.ucapdm2025.taskspaces.data.remote.services.MediaService
import com.ucapdm2025.taskspaces.data.remote.services.UserService
import com.ucapdm2025.taskspaces.helpers.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.IOException
import java.time.LocalDateTime

/**
 * UserRepositoryImpl is an implementation of the UserRepository interface.
 * It provides methods to manage users in the application, including retrieving,
 * creating, updating, and deleting users.
 */
// TODO: Replace dummy data with a real local database, or consuming from a remote API
class UserRepositoryImpl(
    private val context: Context,
    private val userDao: UserDao,
    private val userService: UserService,
    private val authService: AuthService,
    private val mediaService: MediaService
) : UserRepository {
    private var autoIncrementId = 0

    override fun getUsers(): Flow<List<UserModel>> {
        return userDao.getUsers().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getUserById(id: Int): Flow<Resource<UserModel?>> = flow {
        emit(Resource.Loading)

        try {
            val remoteUser: UserResponse? = userService.getUserById(id).content

            if (remoteUser != null) {
                userDao.createUser(remoteUser.toEntity())
            }
        }
        catch(e: Exception) {
            Log.d("UserRepository: getUserById", "Error fetching user: ${e.message}")
        }

        val localUser = userDao.getUserById(id).map { entity ->
            val user = entity?.toDomain()

            if (user != null) {
                Resource.Success(user)
            } else {
                Resource.Error("User not found")
            }
        }.distinctUntilChanged()

        emitAll(localUser)
    }.flowOn(Dispatchers.IO)

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun createUser(
        fullname: String,
        username: String,
        email: String,
        avatar: String,
        password: String,
        confirmPassword: String
    ): Result<UserModel> {
        val request = SignUpRequest(
            fullname = fullname,
            username = username,
            email = email,
            avatar = avatar,
            password = password,
            confirmPassword = confirmPassword
        )

        return try {
            val response = authService.register(request)

            val createdUser: UserModel = response.content.toDomain()

            userDao.createUser(user = createdUser.toDatabase())

            Log.d("UserRepository: createUser", "User created successfully: $createdUser")

            Result.success(createdUser)
        } catch (e: HttpException) {
            Log.e("UserRepository: createUser", "Error creating user: ${e.message}")
            Result.failure(e)
        } catch (e: IOException) {
            Log.e("UserRepository: createUser", "Network error: ${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("UserRepository: createUser", "Unexpected error: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun updateUser(
        id: Int,
        fullname: String,
        username: String,
        email: String,
        avatar: String
    ) {
        val updatedUserModel = UserModel(
            id = id,
            fullname = fullname,
            username = username,
            email = email,
            avatar = avatar,
            createdAt = LocalDateTime.now().toString(),
            updatedAt = LocalDateTime.now().toString()
        )

        userDao.updateUser(updatedUserModel.toDatabase())
    }

    override suspend fun deleteUser(id: Int) {
        userDao.deleteUser(id)
    }

    override suspend fun uploadAvatar(uri: Uri): Result<MediaResponse> {
        return try {
            val contentResolver = context.contentResolver
            val inputStream = contentResolver.openInputStream(uri) ?: return Result.failure(Exception("Cannot open image"))
            val fileName = uri.lastPathSegment?.split("/")?.lastOrNull() ?: "avatar.jpg"
            val bytes = inputStream.readBytes()
            val mimeType = contentResolver.getType(uri) ?: "image/jpeg"
            val requestBody = RequestBody.create(mimeType.toMediaTypeOrNull(), bytes)
            val part = MultipartBody.Part.createFormData("media", "$fileName.${mimeType.split("/").last()}", requestBody)
            val response = mediaService.uploadMedia(part)
            if (response.status in 200..299 && response.content != null) {
                Result.success(response.content)
            } else if (response.content != null) {
                // Accept as success if content is present, even if status is not 2xx
                Result.success(response.content)
            } else {
                Result.failure(Exception(response.message ?: ""))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

