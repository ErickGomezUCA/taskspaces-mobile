package com.ucapdm2025.taskspaces.data.model

data class ProjectModel (
    override val id: Int,
    val title: String,
    val icon: String? = null,
    val workspaceId: Int,
    override val createdAt: String = "",
    override val updatedAt: String = ""
): BaseModel(id, createdAt, updatedAt)