package com.ucapdm2025.taskspaces.data.remote.responses

import com.ucapdm2025.taskspaces.data.model.UserModel

data class AuthLoginResponse(
    val id: Int,
    val fullname: String,
    val username: String,
    val avatar: String,
    val email: String
)

data class AuthRegisterResponse(
    val id: Int,
    val fullname: String,
    val username: String,
    val avatar: String,
    val email: String,
    val createdAt: String,
    val updatedAt: String
)

fun AuthLoginResponse.toDomain(): UserModel {
    return UserModel(
        id = id,
        fullname = fullname,
        username = username,
        avatar = avatar,
        email = email,
        createdAt = "",
        updatedAt = ""
    )
}

fun AuthLoginResponse.toEntity(): UserModel {
    return UserModel(
        id = id,
        fullname = fullname,
        username = username,
        avatar = avatar,
        email = email,
        createdAt = "",
        updatedAt = ""
    )
}

fun AuthRegisterResponse.toDomain(): UserModel {
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