package com.ucapdm2025.taskspaces.data.repository.user

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
import com.ucapdm2025.taskspaces.data.remote.responses.MediaResponse
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
import java.io.IOException
import java.time.LocalDateTime

/**
 * UserRepositoryImpl is an implementation of the UserRepository interface.
 * It provides methods to manage users in the application, including retrieving,
 * creating, updating, and deleting users.
 */
// TODO: Replace dummy data with a real local database, or consuming from a remote API
class UserRepositoryImpl(
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

    }
}