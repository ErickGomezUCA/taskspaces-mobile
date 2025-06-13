package com.ucapdm2025.taskspaces.data.repository.user

import com.ucapdm2025.taskspaces.data.model.UserModel
import com.ucapdm2025.taskspaces.helpers.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<List<UserModel>>
    fun getUserById(id: Int): Flow<Resource<UserModel?>>
    suspend fun createUser(fullname: String, username: String, email: String, avatar: String)
    suspend fun updateUser(id: Int, fullname: String, username: String, email: String, avatar: String)
    suspend fun deleteUser(id: Int)
}