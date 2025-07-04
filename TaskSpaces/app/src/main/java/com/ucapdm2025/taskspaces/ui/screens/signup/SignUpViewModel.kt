package com.ucapdm2025.taskspaces.ui.screens.signup

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.model.UserModel
import com.ucapdm2025.taskspaces.data.repository.auth.AuthRepository
import com.ucapdm2025.taskspaces.data.repository.user.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _fullname = MutableStateFlow<String>("")
    val fullname: StateFlow<String> = _fullname.asStateFlow()

    private val _username = MutableStateFlow<String>("")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _email = MutableStateFlow<String>("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow<String>("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow<String>("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()

    private val _avatarUri = MutableStateFlow<Uri?>(null)
    val avatarUri: StateFlow<Uri?> = _avatarUri.asStateFlow()

    fun setAvatarUri(uri: Uri?) {
        _avatarUri.value = uri
    }

    val authToken: StateFlow<String> = authRepository.authToken.map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ""
        )

    private val _fullnameError = MutableStateFlow<String?>(null)
    val fullnameError: StateFlow<String?> = _fullnameError.asStateFlow()

    private val _usernameError = MutableStateFlow<String?>(null)
    val usernameError: StateFlow<String?> = _usernameError.asStateFlow()

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError.asStateFlow()

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError.asStateFlow()

    private val _confirmPasswordError = MutableStateFlow<String?>(null)
    val confirmPasswordError: StateFlow<String?> = _confirmPasswordError.asStateFlow()

    fun setFullname(name: String) {
        _fullname.value = name
    }

    fun setUsername(username: String) {
        _username.value = username
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun setConfirmPassword(confirmPassword: String) {
        _confirmPassword.value = confirmPassword
    }

    fun signup() {
        // Reset errors
        _fullnameError.value = null
        _usernameError.value = null
        _emailError.value = null
        _passwordError.value = null
        _confirmPasswordError.value = null

        var hasError = false
        if (_fullname.value.isBlank()) {
            _fullnameError.value = "Full name is required"
            hasError = true
        }
        if (_username.value.isBlank()) {
            _usernameError.value = "Username is required"
            hasError = true
        }
        if (_email.value.isBlank()) {
            _emailError.value = "Email is required"
            hasError = true
        } else if (!Regex("^[^@\\s]+@[^@\\s]+\\.[a-zA-Z]{2,}$").matches(_email.value)) {
            _emailError.value = "Invalid email format"
            hasError = true
        }
        if (_password.value.isBlank()) {
            _passwordError.value = "Password is required"
            hasError = true
        } else if (_password.value.length < 6) {
            _passwordError.value = "Password must be at least 6 characters"
            hasError = true
        }
        if (_confirmPassword.value.isBlank()) {
            _confirmPasswordError.value = "Please confirm your password"
            hasError = true
        } else if (_confirmPassword.value != _password.value) {
            _confirmPasswordError.value = "Passwords do not match"
            hasError = true
        }
        if (hasError) return

        viewModelScope.launch {
            val response = userRepository.createUser(
                _fullname.value,
                _username.value,
                _email.value,
                "",
                _password.value,
                _confirmPassword.value
            )

            if (response.isSuccess) {
                val loginResponse = authRepository.login(_email.value, _password.value)

                if (loginResponse.isSuccess) {
//                Save token and user ID from remote server
                    val token = loginResponse.getOrThrow().token
                    val userId = loginResponse.getOrThrow().user.id

                    saveToken(token)
                    saveAuthUserId(userId)
                    // Simplified error handling: combine exception check and fallback message in one line
                }

            } else {
                // Handle error, e.g., show a message to the user
                val exception = response.exceptionOrNull()
                if (exception != null) {
                    // Log or handle the exception as needed
                    Log.e("WorkspaceViewModel", "Error creating project: ${exception.message}")
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
}

/**
 * Factory for creating instances of [SignUpViewModel].
 *
 * @param userRepository The repository for user-related operations.
 */
class SignUpViewModelFactory(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignUpViewModel(
                userRepository,
                authRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

