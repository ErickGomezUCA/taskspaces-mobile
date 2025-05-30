package com.ucapdm2025.taskspaces.data.repository.user

import com.ucapdm2025.taskspaces.data.dummy.usersDummy
import com.ucapdm2025.taskspaces.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime

// TODO: Replace dummy data with a real local database, or consuming from a remote API
class UserRepositoryImpl : UserRepository {
    private val users = MutableStateFlow(usersDummy)
    private var autoIncrementId = users.value.size + 1

    override fun getUsers(): Flow<List<User>> {
        return users.asStateFlow()
    }

    override fun getUserById(id: Int): Flow<User?> {
        return users.value.find { it.id == id }
            ?.let { MutableStateFlow(it) }
            ?: MutableStateFlow(null)
    }

    override suspend fun createUser(
        fullname: String,
        username: String,
        email: String,
        avatar: String
    ): User {
        val createdUser = User(
            id = autoIncrementId++,
            fullname = fullname,
            username = username,
            email = email,
            avatar = avatar,
            createdAt = LocalDateTime.now().toString(),
            updatedAt = LocalDateTime.now().toString()
        )

        users.value = users.value + createdUser
        return createdUser
    }

    override suspend fun updateUser(
        id: Int,
        fullname: String,
        username: String,
        email: String,
        avatar: String
    ): User {
        val updatedUser = User(
            id = id,
            fullname = fullname,
            username = username,
            email = email,
            avatar = avatar,
            createdAt = LocalDateTime.now().toString(),
            updatedAt = LocalDateTime.now().toString()
        )

        users.value = users.value.map {
            if (it.id == updatedUser.id) updatedUser else it
        }
        return updatedUser
    }

    override suspend fun deleteUser(id: Int): Boolean {
        val exists = users.value.any { it.id == id }

        if (exists) {
            users.value = users.value.filter { it.id != id }
        }

        return exists
    }
}