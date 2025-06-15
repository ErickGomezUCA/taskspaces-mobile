package com.ucapdm2025.taskspaces.data.remote.responses

import com.ucapdm2025.taskspaces.data.database.entities.ProjectEntity
import com.ucapdm2025.taskspaces.data.model.ProjectModel

/**
 * ProjectResponse is a data class that represents the response from the API for a project.
 *
 * @property id The unique identifier for the project.
 * @property title The title of the project.
 * @property icon An optional icon for the project, represented as a string.
 * @property workspaceId The ID of the workspace to which the project belongs.
 * @property createdAt The timestamp when the project was created.
 * @property updatedAt The timestamp when the project was last updated.
 */
data class ProjectResponse (
    val id: Int,
    val title: String,
    val icon: String? = null,
    val workspaceId: Int,
    val createdAt: String = "",
    val updatedAt: String = ""
)

/**
 * Converts ProjectResponse to ProjectModel for application use.
 *
 * @return ProjectModel representing the project in the application.
 */
fun ProjectResponse.toDomain(): ProjectModel {
    return ProjectModel(
        id = id,
        title = title,
        icon = icon ?: "",
        workspaceId = workspaceId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

/**
 * Converts ProjectResponse to ProjectModel for database storage.
 *
 * @return ProjectModel representing the project in the database.
 */
fun ProjectResponse.toEntity(): ProjectEntity {
    return ProjectEntity(
        id = id,
        title = title,
        icon = icon ?: "",
        workspaceId = workspaceId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}