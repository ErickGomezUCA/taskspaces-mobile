package com.ucapdm2025.taskspaces.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ucapdm2025.taskspaces.data.model.WorkspaceModel

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