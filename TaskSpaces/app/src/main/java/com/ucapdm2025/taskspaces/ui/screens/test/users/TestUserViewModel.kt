package com.ucapdm2025.taskspaces.ui.screens.test.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.model.UserModel
import com.ucapdm2025.taskspaces.data.repository.user.UserRepository
import com.ucapdm2025.taskspaces.data.repository.user.UserRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TestUserViewModel: ViewModel() {
    private val usersRepository: UserRepository = UserRepositoryImpl()

    private val _users: MutableStateFlow<List<UserModel>> = MutableStateFlow(emptyList())
    val users: StateFlow<List<UserModel>> = _users.asStateFlow()

    init {
        viewModelScope.launch {

            usersRepository.getUsers().collect { userList ->
                _users.value = userList
            }
        }
    }

    fun getUserById(id: Int): UserModel? {
        var userModel: UserModel? = null

        viewModelScope.launch {
            usersRepository.getUserById(id).collect { foundUser ->
                userModel = foundUser
            }
        }

        return userModel
    }

    fun createUser(fullname: String, username: String, email: String, avatar: String) {
        viewModelScope.launch {
            usersRepository.createUser(fullname, username, email, avatar)
        }
    }

    fun updateUser(id: Int, fullname: String, username: String, email: String, avatar: String) {
        viewModelScope.launch {
            usersRepository.updateUser(id, fullname, username, email, avatar)
        }
    }

    fun deleteUser(id: Int) {
        viewModelScope.launch {
            usersRepository.deleteUser(id)
        }
    }
}