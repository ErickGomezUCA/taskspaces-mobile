package com.ucapdm2025.taskspaces.data.database.entities.relational

import androidx.room.Entity
import com.ucapdm2025.taskspaces.data.model.relational.TaskTagModel

/**
 * TaskTagEntity is a data class that represents a many-to-many relationship between tasks and tags in the database.
 */
@Entity(tableName = "task_tag", primaryKeys = ["taskId", "tagId"])
data class TaskTagEntity (
    val taskId: Int,
    val tagId: Int,
    val createdAt: String? = null
)

fun TaskTagEntity.toDomain(): TaskTagModel {
    return TaskTagModel(
        taskId = taskId,
        tagId = tagId,
        createdAt = createdAt
    )
}