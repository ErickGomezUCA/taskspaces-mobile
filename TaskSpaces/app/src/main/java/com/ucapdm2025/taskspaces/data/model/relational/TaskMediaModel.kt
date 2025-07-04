package com.ucapdm2025.taskspaces.data.model.relational

import com.ucapdm2025.taskspaces.data.database.entities.relational.TaskMediaEntity

/**
 * TaskMediaModel represents the relationship between a task and its associated media files.
 *
 * @property taskId The unique identifier for the task.
 * @property mediaId The unique identifier for the media file.
 * * @property createdAt The timestamp when the media was associated with the task.
 */
data class TaskMediaModel(
    val taskId: Int,
    val mediaId: Int,
    val createdAt: String? = null,
)

/**
 * Converts TaskMediaModel to TaskMediaEntity for database storage.
 *
 * @return TaskMediaEntity representing the relationship in the database.
 */
fun TaskMediaModel.toDatabase(): TaskMediaEntity {
    return TaskMediaEntity(
        taskId = taskId,
        mediaId = mediaId,
        createdAt = createdAt
    )
}