package com.ucapdm2025.taskspaces.data.repository.user

import android.util.Log
import com.ucapdm2025.taskspaces.data.database.dao.UserDao
import com.ucapdm2025.taskspaces.data.database.entities.relational.toDomain
import com.ucapdm2025.taskspaces.data.model.UserModel
import com.ucapdm2025.taskspaces.data.model.relational.toDatabase
import com.ucapdm2025.taskspaces.data.remote.responses.UserResponse
import com.ucapdm2025.taskspaces.data.remote.responses.workspace.toEntity
import com.ucapdm2025.taskspaces.data.remote.services.UserService
import com.ucapdm2025.taskspaces.helpers.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime

/**
 * UserRepositoryImpl is an implementation of the UserRepository interface.
 * It provides methods to manage users in the application, including retrieving,
 * creating, updating, and deleting users.
 */
// TODO: Replace dummy data with a real local database, or consuming from a remote API
class UserRepositoryImpl(
    private val userDao: UserDao,
    private val userService: UserService
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

    override suspend fun createUser(
        fullname: String,
        username: String,
        email: String,
        avatar: String
    ) {
        val createdUserModel = UserModel(
            id = autoIncrementId++,
            fullname = fullname,
            username = username,
            email = email,
            avatar = avatar,
            createdAt = LocalDateTime.now().toString(),
            updatedAt = LocalDateTime.now().toString()
        )

        userDao.createUser(createdUserModel.toDatabase())
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
}