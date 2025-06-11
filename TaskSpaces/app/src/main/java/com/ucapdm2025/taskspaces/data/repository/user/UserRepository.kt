package com.ucapdm2025.taskspaces.data.repository.user

import com.ucapdm2025.taskspaces.data.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<List<UserModel>>
    fun getUserById(id: Int): Flow<UserModel?>
    suspend fun createUser(fullname: String, username: String, email: String, avatar: String): UserModel
    suspend fun updateUser(id: Int, fullname: String, username: String, email: String, avatar: String): UserModel
    suspend fun deleteUser(id: Int): Boolean
}