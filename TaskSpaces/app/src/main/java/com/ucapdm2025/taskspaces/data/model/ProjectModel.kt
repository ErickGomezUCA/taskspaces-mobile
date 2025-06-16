package com.ucapdm2025.taskspaces.data.model

import com.ucapdm2025.taskspaces.data.database.entities.ProjectEntity

/**
 * ProjectModel represents a project in the system.
 * It extends BaseModel to include common properties
 * like id, createdAt, and updatedAt.
 *
 * @property id The unique identifier for the project.
 * @property title The title of the project.
 * @property icon An optional icon for the project.
 * @property workspaceId The ID of the workspace to which the project belongs.
 * @property createdAt The timestamp when the project was created.
 * @property updatedAt The timestamp when the project was last updated.
 */
data class ProjectModel (
    override val id: Int,
    val title: String,
    val icon: String? = null,
    val workspaceId: Int,
    override val createdAt: String? = null,
    override val updatedAt: String? = null
): BaseModel(id, createdAt, updatedAt)

/**
 * Converts ProjectModel to ProjectEntity for database storage.
 *
 * @return ProjectEntity representing the project in the database.
 */
fun ProjectModel.toDatabase(): ProjectEntity {
    return ProjectEntity(
        id = id,
        title = title,
        icon = icon ?: "",
        workspaceId = workspaceId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}