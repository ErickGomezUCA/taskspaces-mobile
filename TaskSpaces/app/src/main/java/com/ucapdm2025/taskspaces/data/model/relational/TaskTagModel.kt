package com.ucapdm2025.taskspaces.data.model.relational

import com.ucapdm2025.taskspaces.data.database.entities.relational.TaskTagEntity

/**
 * TaskTagModel is a data class that represents a many-to-many relationship between tasks and tags.
 *
 * @property taskId The ID of the task associated with the tag.
 * @property tagId The ID of the tag associated with the task.
 * @property createdAt The timestamp when the task-tag relationship was created, represented as a String.
 */
data class TaskTagModel (
    val taskId: Int,
    val tagId: Int,
    val createdAt: String? = null
)

/**
 * Converts TaskTagModel to TaskTagEntity for database representation.
 *
 * @return TaskTagEntity representing the task-tag relationship in the database layer.
 */
fun TaskTagModel.toDatabase(): TaskTagEntity {
    return TaskTagEntity(
        taskId = taskId,
        tagId = tagId,
        createdAt = createdAt
    )
}
