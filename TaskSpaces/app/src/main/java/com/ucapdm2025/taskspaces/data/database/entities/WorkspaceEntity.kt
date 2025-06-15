package com.ucapdm2025.taskspaces.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ucapdm2025.taskspaces.data.model.WorkspaceModel

/**
 * WorkspaceEntity is a data class that represents a workspace in the database.
 * It extends BaseEntity to include common fields such as id, createdAt, and updatedAt.
 *
 * @property id The unique identifier for the workspace.
 * @property title The title of the workspace.
 * @property ownerId The ID of the user who owns the workspace.
 * @property createdAt The timestamp when the workspace was created.
 * @property updatedAt The timestamp when the workspace was last updated.
 */
@Entity(tableName = "workspace")
data class WorkspaceEntity (
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    val title: String,
    val ownerId: Int,
    override val createdAt: String = "",
    override val updatedAt: String = ""
): BaseEntity(id, createdAt, updatedAt)

fun WorkspaceEntity.toDomain(): WorkspaceModel {
    return WorkspaceModel(
        id = id,
        title = title,
        ownerId = ownerId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}