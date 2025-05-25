package com.ucapdm2025.taskspaces.data.model

data class Tag (
    override val id: Int,
    val title: String,
    val color: String,
    override val createdAt: String,
    override val updatedAt: String,
): BaseModel(id, createdAt, updatedAt)