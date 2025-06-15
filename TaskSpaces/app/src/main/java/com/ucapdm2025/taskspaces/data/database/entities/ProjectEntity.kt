package com.ucapdm2025.taskspaces.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ucapdm2025.taskspaces.data.model.ProjectModel

/**
 * ProjectEntity is a data class that represents a project in the database.
 *
 * @param id The unique identifier for the project.
 * @param title The title of the project.
 * @param icon The icon associated with the project.
 * @param workspaceId The ID of the workspace to which the project belongs.
 * @param createdAt The timestamp when the project was created.
 * @param updatedAt The timestamp when the project was last updated.
 */
@Entity(tableName = "project")
data class ProjectEntity (
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    val title: String,
    val icon: String,
    val workspaceId: Int,
    override val createdAt: String = "",
    override val updatedAt: String = ""
): BaseEntity(id, createdAt, updatedAt)

/**
 * Extension function to convert a ProjectEntity to a ProjectModel.
 *
 * @return A ProjectModel instance with the same properties as the ProjectEntity.
 */
fun ProjectEntity.toDomain(): ProjectModel {
    return ProjectModel(
        id = id,
        title = title,
        icon = icon,
        workspaceId = workspaceId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}