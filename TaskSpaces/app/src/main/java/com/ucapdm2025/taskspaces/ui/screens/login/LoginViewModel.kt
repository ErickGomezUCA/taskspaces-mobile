package com.ucapdm2025.taskspaces.ui.screens.login

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
): ViewModel() {
    val authToken: StateFlow<String> = authRepository.authToken.map { it }
            .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ""
    )

    fun login(
        email: String,
        password: String
    ) {
        // Here you would typically call a login function from the repository
        // For example:
        // viewModelScope.launch {
        //     authRepository.login(email, password)
        // }
        // This is just a placeholder for demonstration purposes.
        viewModelScope.launch {
            authRepository.login(email, password)
        }
    }

    fun saveToken(
        token: String
    ) {
        viewModelScope.launch {
            authRepository.saveAuthToken(token)
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
