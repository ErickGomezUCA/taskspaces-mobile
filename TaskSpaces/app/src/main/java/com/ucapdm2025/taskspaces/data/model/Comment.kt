package com.ucapdm2025.taskspaces.data.model

data class Comment(
    override val id: Int,
    val content: String,
    val authorId: Int,
    val taskId: Int,
    override val createdAt: String,
    override val updatedAt: String,
): BaseModel(id, createdAt, updatedAt)