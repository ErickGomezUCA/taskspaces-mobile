package com.ucapdm2025.taskspaces.data.remote.responses

/**
 * LoginResponse is a data class that represents the response from a user login API call.
 * It contains the authentication token and user details.
 *
 * @property token The authentication token received upon successful login.
 * @property user The details of the user who has logged in.
 */
data class LoginResponse(
    val token: String,
    val user: UserResponse
)