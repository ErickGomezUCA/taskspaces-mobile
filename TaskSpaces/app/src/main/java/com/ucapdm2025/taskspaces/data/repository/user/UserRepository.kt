package com.ucapdm2025.taskspaces.data.repository.user

import com.ucapdm2025.taskspaces.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<List<User>>
    fun getUserById(id: Int): Flow<User?>
    suspend fun createUser(user: User): User
    suspend fun updateUser(user: User): User
    suspend fun deleteUser(id: Int): Boolean
}