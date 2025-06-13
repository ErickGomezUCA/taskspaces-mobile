package com.ucapdm2025.taskspaces.data.database.entities

import androidx.room.Entity
import com.ucapdm2025.taskspaces.data.model.UserModel

@Entity(tableName = "user")
data class UserEntity(
    override val id: Int,
    val fullname: String,
    val username: String,
    val email: String,
    val avatar: String? = null,
    override val createdAt: String = "",
    override val updatedAt: String = ""
) : BaseEntity(id, createdAt, updatedAt)

fun UserEntity.toDomain(): UserModel {
    return UserModel(
        id = id,
        fullname = "",
        username = username,
        avatar = "",
        email = email,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}