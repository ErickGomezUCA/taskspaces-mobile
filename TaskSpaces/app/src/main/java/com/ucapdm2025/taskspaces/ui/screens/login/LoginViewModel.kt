package com.ucapdm2025.taskspaces.ui.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ucapdm2025.taskspaces.TaskSpacesApplication
import com.ucapdm2025.taskspaces.data.repository.auth.AuthRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
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

    fun login(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            val response = authRepository.login(email.trim(), password.trim())

            if (response.isSuccess) {
//                Save token and user ID from remote server
                val token = response.getOrThrow().token
                val userId = response.getOrThrow().user.id

                saveToken(token)
                saveAuthUserId(userId)
            } else {
                // Handle login failure, e.g., show a message to the user
                val exception = response.exceptionOrNull()
                if (exception != null) {
                    // Log or handle the exception as needed
                    Log.d("test1", "Login failed: ${exception.message}")
                }
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
