package com.ucapdm2025.taskspaces.data.remote.responses.workspace

import com.ucapdm2025.taskspaces.data.database.entities.WorkspaceEntity
import com.ucapdm2025.taskspaces.data.model.WorkspaceModel

/**
 * WorkspaceResponse is a data class that represents the response from the API for a workspace.
 *
 * @property id The unique identifier for the workspace.
 * @property title The title of the workspace.
 * @property ownerId The ID of the user who owns the workspace.
 * @property createdAt The timestamp when the workspace was created.
 * @property updatedAt The timestamp when the workspace was last updated.
 */
data class WorkspaceResponse (
    val id: Int,
    val title: String,
    val ownerId: Int,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

/**
 * Converts WorkspaceResponse to WorkspaceModel for application use.
 *
 * @return WorkspaceModel representing the workspace in the application.
 */
fun WorkspaceResponse.toDomain(): WorkspaceModel {
    return WorkspaceModel(
        id = id,
        title = title,
        ownerId = ownerId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

/**
 * Converts WorkspaceResponse to WorkspaceEntity for database storage.
 *
 * @return WorkspaceEntity representing the workspace in the database.
 */
fun WorkspaceResponse.toEntity(): WorkspaceEntity {
    return WorkspaceEntity(
        id = id,
        title = title,
        ownerId = ownerId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}