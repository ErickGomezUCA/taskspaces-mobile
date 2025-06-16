package com.ucapdm2025.taskspaces.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ucapdm2025.taskspaces.data.model.UserModel

/**
 * UserEntity is a data class that represents a user in the database.
 * It extends BaseEntity to include common fields such as id, createdAt, and updatedAt.
 *
 * @property id The unique identifier for the user.
 * @property fullname The full name of the user.
 * @property username The username of the user.
 * @property email The email address of the user.
 * @property avatar The avatar URL of the user (optional).
 * @property createdAt The timestamp when the user was created.
 * @property updatedAt The timestamp when the user was last updated.
 */
@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Int,
    val fullname: String,
    val username: String,
    val email: String,
    val avatar: String? = null,
    override val createdAt: String? = null,
    override val updatedAt: String? = null
) : BaseEntity(id, createdAt, updatedAt)

fun UserEntity.toDomain(): UserModel {
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