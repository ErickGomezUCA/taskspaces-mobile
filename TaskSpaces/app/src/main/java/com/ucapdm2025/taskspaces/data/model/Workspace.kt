package com.ucapdm2025.taskspaces.data.model

data class Workspace(
    override val id: Int,
    val title: String,
    val ownerId: Int,
    override val createdAt: String,
    override val updatedAt: String,
): BaseModel(id, createdAt, updatedAt)