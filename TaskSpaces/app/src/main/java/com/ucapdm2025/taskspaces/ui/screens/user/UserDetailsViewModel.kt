package com.ucapdm2025.taskspaces.ui.screens.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ucapdm2025.taskspaces.TaskSpacesApplication
import com.ucapdm2025.taskspaces.data.model.UserModel
import com.ucapdm2025.taskspaces.data.repository.auth.AuthRepository
import com.ucapdm2025.taskspaces.data.repository.user.UserRepository
import com.ucapdm2025.taskspaces.helpers.Resource
import com.ucapdm2025.taskspaces.helpers.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserDetailsViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableStateFlow<UiState<UserModel>>(UiState.Loading)
    val user: StateFlow<UiState<UserModel>> = _user.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.authUserId.collect { userId ->
                if (userId != null) {
                    userRepository.getUserById(userId).collect { resource ->
                        when (resource) {
                            is Resource.Loading -> _user.value = UiState.Loading
                            is Resource.Success -> {
                                val userData = resource.data
                                if (userData != null) {
                                    _user.value = UiState.Success(userData)
                                } else {
                                    _user.value = UiState.Error("User not found")
                                }
                            }
                            is Resource.Error -> {
                                Log.e("UserDetailsViewModel", resource.message)
                                _user.value = UiState.Error(resource.message)
                            }
                        }
                    }
                } else {
                    _user.value = UiState.Error("No authenticated user")
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TaskSpacesApplication
                UserDetailsViewModel(
                    app.appProvider.provideAuthRepository(),
                    app.appProvider.provideUserRepository()
                )
            }
        }
    }
}