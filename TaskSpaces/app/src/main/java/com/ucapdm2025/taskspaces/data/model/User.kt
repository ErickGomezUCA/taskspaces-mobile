package com.ucapdm2025.taskspaces.data.model

data class User (
    override val id: Int,
    val fullname: String,
    val username: String,
    val email: String,
    val avatar: String,
    override val createdAt: String,
    override val updatedAt: String,
): BaseModel(id, createdAt, updatedAt)