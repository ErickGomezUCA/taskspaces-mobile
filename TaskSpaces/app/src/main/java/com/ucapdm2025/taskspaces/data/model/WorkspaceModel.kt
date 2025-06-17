package com.ucapdm2025.taskspaces.data.model

import com.ucapdm2025.taskspaces.data.database.entities.WorkspaceEntity

/**
 * WorkspaceModel represents a workspace in the application, which can contain projects and tasks.
 *
 * @property id The unique identifier for the workspace.
 * @property title The title of the workspace.
 * @property ownerId The ID of the user who owns the workspace.
 * @property createdAt The timestamp when the workspace was created.
 * @property updatedAt The timestamp when the workspace was last updated.
 */
data class WorkspaceModel(
    override val id: Int,
    val title: String,
    val ownerId: Int,
    override val createdAt: String? = null,
    override val updatedAt: String? = null,
): BaseModel(id, createdAt, updatedAt)

/**
 * Converts WorkspaceModel to WorkspaceEntity for database storage.
 *
 * @return WorkspaceEntity representing the workspace in the database.
 */
fun WorkspaceModel.toDatabase(): WorkspaceEntity {
    return WorkspaceEntity(
        id = id,
        title = title,
        ownerId = ownerId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}