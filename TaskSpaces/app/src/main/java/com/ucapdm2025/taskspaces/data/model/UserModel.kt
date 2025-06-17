package com.ucapdm2025.taskspaces.data.model

import com.ucapdm2025.taskspaces.data.database.entities.UserEntity

/**
 * UserModel represents a user in the application, which can be associated with projects and tasks.
 *
 * @property id The unique identifier for the user.
 * @property fullname The full name of the user.
 * @property username The username of the user.
 * @property email The email address of the user.
 * @property avatar The URL of the user's avatar image, if available.
 * @property createdAt The timestamp when the user was created.
 * @property updatedAt The timestamp when the user was last updated.
 */
data class UserModel (
    override val id: Int,
    val fullname: String,
    val username: String,
    val email: String,
    val avatar: String? = null,
    override val createdAt: String? = null,
    override val updatedAt: String? = null,
): BaseModel(id, createdAt, updatedAt)

/**
 * Converts UserModel to UserEntity for database storage.
 *
 * @return UserEntity representing the user in the database.
 */
fun UserModel.toDatabase(): UserEntity {
    return UserEntity(
        id = id,
        fullname = fullname,
        username = username,
        email = email,
        avatar = avatar,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}