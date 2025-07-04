package com.ucapdm2025.taskspaces.data.repository.user

import com.ucapdm2025.taskspaces.data.model.UserModel
import com.ucapdm2025.taskspaces.helpers.Resource
import kotlinx.coroutines.flow.Flow

/**
 * UserRepository is an interface that defines the methods for managing users in the application.
 * It provides methods to get a list of users, get a user by ID, create a new user, update an existing user,
 * and delete a user.
 */
interface UserRepository {
    fun getUsers(): Flow<List<UserModel>>
    fun getUserById(id: Int): Flow<Resource<UserModel?>>
    suspend fun createUser(fullname: String, username: String, email: String, avatar: String, password: String, confirmPassword: String): Result<UserModel>
    suspend fun updateUser(id: Int, fullname: String, username: String, email: String, avatar: String)
    suspend fun deleteUser(id: Int)
}