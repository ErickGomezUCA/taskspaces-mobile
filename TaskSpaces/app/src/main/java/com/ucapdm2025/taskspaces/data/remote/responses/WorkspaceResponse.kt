package com.ucapdm2025.taskspaces.data.remote.responses

import com.ucapdm2025.taskspaces.data.database.entities.WorkspaceEntity
import com.ucapdm2025.taskspaces.data.model.WorkspaceModel

data class WorkspaceResponse (
    val id: Int,
    val title: String,
    val ownerId: Int,
    val createdAt: String,
    val updatedAt: String
)

fun WorkspaceResponse.toDomain(): WorkspaceModel {
    return WorkspaceModel(
        id = id,
        title = title,
        ownerId = ownerId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun WorkspaceResponse.toEntity(): WorkspaceEntity {
    return WorkspaceEntity(
        id = id,
        title = title,
        ownerId = ownerId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}