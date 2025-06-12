package com.ucapdm2025.taskspaces.data.model

import com.ucapdm2025.taskspaces.data.database.entities.WorkspaceEntity

data class WorkspaceModel(
    override val id: Int,
    val title: String,
    val ownerId: Int,
    override val createdAt: String = "",
    override val updatedAt: String = "",
): BaseModel(id, createdAt, updatedAt)

fun WorkspaceModel.toDatabase(): WorkspaceEntity {
    return WorkspaceEntity(
        id = id,
        title = title,
        ownerId = ownerId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}