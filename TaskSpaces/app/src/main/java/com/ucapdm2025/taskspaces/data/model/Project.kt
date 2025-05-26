package com.ucapdm2025.taskspaces.data.model

data class Project (
    override val id: Int,
    val title: String,
    val icon: String,
    override val createdAt: String,
    override val updatedAt: String
): BaseModel(id, createdAt, updatedAt)