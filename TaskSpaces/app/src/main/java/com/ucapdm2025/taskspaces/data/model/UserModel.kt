package com.ucapdm2025.taskspaces.data.model

import com.ucapdm2025.taskspaces.data.database.entities.UserEntity

data class UserModel (
    override val id: Int,
    val fullname: String,
    val username: String,
    val email: String,
    val avatar: String? = null,
    override val createdAt: String = "",
    override val updatedAt: String = "",
): BaseModel(id, createdAt, updatedAt)

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