package com.ucapdm2025.taskspaces.data.remote.responses

import com.ucapdm2025.taskspaces.data.database.entities.UserEntity
import com.ucapdm2025.taskspaces.data.model.UserModel

data class UserResponse (
    val id: Int,
    val fullname: String,
    val username: String,
    val avatar: String,
    val email: String,
    val createdAt: String,
    val updatedAt: String
)

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