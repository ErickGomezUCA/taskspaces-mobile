package com.ucapdm2025.taskspaces.data.repository.user

import com.ucapdm2025.taskspaces.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<List<User>>
    fun getUserById(id: Int): Flow<User?>
    suspend fun createUser(fullname: String, username: String, email: String, avatar: String): User
    suspend fun updateUser(id: Int, fullname: String, username: String, email: String, avatar: String): User
    suspend fun deleteUser(id: Int): Boolean
}