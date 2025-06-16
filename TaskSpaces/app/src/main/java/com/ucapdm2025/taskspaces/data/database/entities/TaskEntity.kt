package com.ucapdm2025.taskspaces.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ucapdm2025.taskspaces.data.model.BaseModel
import com.ucapdm2025.taskspaces.data.model.TaskModel
import com.ucapdm2025.taskspaces.ui.components.projects.StatusVariations
import java.time.LocalDateTime

// Tags, assignedMembers and comments should be handled in separate entities
@Entity(tableName = "task")
data class TaskEntity (
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    val breadcrumb: String = "/",
    val title: String,
    val description: String? = null,
    val deadline: String? = null,
    val timer: Float? = null,
    val status: String,
    val projectId: Int,
    override val createdAt: String? = null,
    override val updatedAt: String? = null
): BaseModel(id, createdAt, updatedAt)

fun TaskEntity.toDomain(): TaskModel {
    return TaskModel(
        id = id,
        breadcrumb = breadcrumb,
        title = title,
        description = description,
        deadline = LocalDateTime.now(),
        timer = timer,
        status = when (status.uppercase()) {
            "PENDING" -> StatusVariations.PENDING
            "DOING" -> StatusVariations.DOING
            "DONE" -> StatusVariations.DONE
            else -> StatusVariations.PENDING // Default case
        },
        projectId = projectId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}