package com.ucapdm2025.taskspaces.data.remote.responses

import com.ucapdm2025.taskspaces.data.database.entities.UserEntity
import com.ucapdm2025.taskspaces.data.model.UserModel

/**
 * UserResponse is a data class that represents the response from the user API.
 * It contains user details such as id, fullname, username, avatar, email, and timestamps.
 *
 * @property id The unique identifier for the user.
 * @property fullname The full name of the user.
 * @property username The username of the user.
 * @property avatar The URL of the user's avatar image.
 * @property email The email address of the user.
 * @property createdAt The timestamp when the user was created.
 * @property updatedAt The timestamp when the user was last updated.
 */
data class UserResponse (
    val id: Int,
    val fullname: String,
    val username: String,
    val avatar: String,
    val email: String,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

/**
 * Converts UserResponse to UserModel for application use.
 *
 * @return UserModel representing the user in the application.
 */
fun UserResponse.toDomain(): UserModel {
    return UserModel(
        id = id,
        fullname = fullname,
        username = username,
        avatar = avatar,
        email = email,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

/**
 * Converts UserResponse to UserEntity for database storage.
 *
 * @return UserEntity representing the user in the database.
 */
fun UserResponse.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        fullname = fullname,
        username = username,
        avatar = avatar,
        email = email,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}