package com.ucapdm2025.taskspaces.data.remote.requests

/**
 * SignUpRequest is a data class that represents the request body for user registration.
 *
 * @property fullname The full name of the user.
 * @property username The username of the user.
 * @property email The email address of the user.
 * @property avatar The URL of the user's avatar image.
 * @property password The password for the user's account.
 * @property confirmPassword The confirmation of the user's password.
 */
data class SignUpRequest(
    val fullname: String,
    val username: String,
    val email: String,
    val avatar: String,
    val password: String,
    val confirmPassword: String
)