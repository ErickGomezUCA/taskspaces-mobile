package com.ucapdm2025.taskspaces.ui.screens.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.repository.user.UserRepository
import com.ucapdm2025.taskspaces.data.repository.user.UserRepositoryImpl
import com.ucapdm2025.taskspaces.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TestViewModel: ViewModel() {
    private val usersRepository: UserRepository = UserRepositoryImpl()

    private val _users: MutableStateFlow<List<User>> = MutableStateFlow(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    init {
        viewModelScope.launch {

            usersRepository.getUsers().collect { userList ->
                _users.value = userList
            }
        }
    }

    fun getUserById(id: Int): User? {
        var user: User? = null

        viewModelScope.launch {
            usersRepository.getUserById(id).collect { foundUser ->
                user = foundUser
            }
        }

        return user
    }
}