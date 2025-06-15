package com.ucapdm2025.taskspaces.data.remote.requests

/**
 * LoginRequest is a data class that represents the request body for user login.
 *
 * @property email The email address of the user.
 * @property password The password of the user.
 */
data class LoginRequest(
    val email: String,
    val password: String
)