package com.ucapdm2025.taskspaces.data.repository.user

import com.ucapdm2025.taskspaces.data.dummy.usersDummy
import com.ucapdm2025.taskspaces.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserRepositoryImpl: UserRepository {
    private val users = MutableStateFlow(usersDummy)

    override fun getUsers(): Flow<List<User>> {
        return users.asStateFlow()
    }

    override suspend fun getUserById(id: Int): User? {
        return users.value.find { it.id == id }
    }

    override suspend fun createUser(user: User): User {
        users.value = users.value + user
        return user
    }

    override suspend fun updateUser(user: User): User {
        users.value = users.value.map {
            if (it.id == user.id) user else it
        }
        return user
    }

    override suspend fun deleteUser(id: Int): Boolean {
        val exists = users.value.any { it.id == id }

        if (exists) {
            users.value = users.value.filter { it.id != id }
        }

        return exists
    }
}