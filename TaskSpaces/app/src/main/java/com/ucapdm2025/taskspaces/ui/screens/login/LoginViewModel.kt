package com.ucapdm2025.taskspaces.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ucapdm2025.taskspaces.TaskSpacesApplication
import com.ucapdm2025.taskspaces.data.repository.auth.AuthRepository
import com.ucapdm2025.taskspaces.helpers.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    val authToken: StateFlow<String> = authRepository.authToken.map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ""
        )

    val authUserId: StateFlow<Int> = authRepository.authUserId.map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0
        )
    // UI state for login process (loading, success, error)
    private val _loginState = MutableStateFlow<UiState<Unit>>(UiState.Success(Unit))
    val loginState: StateFlow<UiState<Unit>> = _loginState.asStateFlow()

    // Automatically trigger login flow if a valid token already exists.
    // without needing to manually log in again.
    init {
        viewModelScope.launch {
            authToken.collect { token ->
                if (token.isNotEmpty()) {
                    _loginState.value = UiState.Loading
                }
            }
        }
    }

    fun login(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _loginState.value = UiState.Loading

            val response = authRepository.login(email.trim(), password.trim())

            if (response.isSuccess) {
//                Save token and user ID from remote server
                val token = response.getOrThrow().token
                val userId = response.getOrThrow().user.id

                saveToken(token)
                saveAuthUserId(userId)
            // Simplified error handling: combine exception check and fallback message in one line
            } else {
                // Handle login failure, e.g., show a message to the user
                val exception = response.exceptionOrNull()?.message ?: "Login failed"
                    _loginState.value = UiState.Error(exception)
            }
        }
    }

    private fun saveToken(
        token: String
    ) {
        viewModelScope.launch {
            authRepository.saveAuthToken(token)
        }
    }

    private fun saveAuthUserId(
        userId: Int
    ) {
        viewModelScope.launch {
            authRepository.saveAuthUserId(userId)
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.saveAuthToken("")
        }

        viewModelScope.launch {
            authRepository.saveAuthUserId(0)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as TaskSpacesApplication)
                LoginViewModel(
                    application.appProvider.provideAuthRepository()
                )
            }
        }
    }
}
