package com.ucapdm2025.taskspaces.ui.screens.test.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ucapdm2025.taskspaces.TaskSpacesApplication
import com.ucapdm2025.taskspaces.data.model.UserModel
import com.ucapdm2025.taskspaces.data.repository.user.UserRepository
import com.ucapdm2025.taskspaces.data.repository.user.UserRepositoryImpl
import com.ucapdm2025.taskspaces.helpers.Resource
import com.ucapdm2025.taskspaces.ui.screens.workspace.WorkspaceViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TestUserViewModel(private val userRepository: UserRepository): ViewModel() {
    private val _users: MutableStateFlow<List<UserModel>> = MutableStateFlow(emptyList())
    val users: StateFlow<List<UserModel>> = _users.asStateFlow()

    init {
        viewModelScope.launch {

            userRepository.getUsers().collect { userList ->
                _users.value = userList
            }
        }
    }

    fun getUserById(id: Int): UserModel? {
        var userModel: UserModel? = null

        viewModelScope.launch {
            userRepository.getUserById(id).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        // Handle loading state if necessary
                    }
                    is Resource.Success -> {
                        userModel = resource.data
                    }
                    is Resource.Error -> {
                        // Handle error state if necessary
                    }
                }
            }
        }

        return userModel
    }

    fun createUser(fullname: String, username: String, email: String, avatar: String) {
        viewModelScope.launch {
            userRepository.createUser(fullname, username, email, avatar)
        }
    }

    fun updateUser(id: Int, fullname: String, username: String, email: String, avatar: String) {
        viewModelScope.launch {
            userRepository.updateUser(id, fullname, username, email, avatar)
        }
    }

    fun deleteUser(id: Int) {
        viewModelScope.launch {
            userRepository.deleteUser(id)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as TaskSpacesApplication
                TestUserViewModel(
                    userRepository = application.appProvider.provideUserRepository()
                )
            }
        }
    }
}