package com.ucapdm2025.taskspaces.data.remote.responses

data class LoginResponse(
    val token: String,
    val user: UserResponse
)