package com.ucapdm2025.taskspaces.data.remote.responses.media

import com.ucapdm2025.taskspaces.data.database.entities.relational.TaskMediaEntity
import com.ucapdm2025.taskspaces.data.model.relational.TaskMediaModel

/**
 * TaskMediaResponse represents the relationship between a task and its associated media files in the API response.
 *
 * @property taskId The unique identifier for the task.
 * @property media The media file associated with the task.
 * @property createdAt The timestamp when the media was associated with the task.
 */
data class TaskMediaResponse(
    val taskId: Int,
    val media: MediaResponse,
    val createdAt: String? = null
)

/**
 * Converts TaskMediaResponse to TaskMediaModel for domain use.
 *
 * @return TaskMediaModel representing the relationship in the domain layer.
 */
fun TaskMediaResponse.toDomain(): TaskMediaModel {
    return TaskMediaModel(
        taskId = taskId,
        mediaId = media.id,
        createdAt = createdAt
    )
}

/**
 * Converts TaskMediaResponse to TaskMediaModel for database storage.
 *
 * @return TaskMediaModel representing the relationship in the database.
 */
fun TaskMediaResponse.toDatabase(): TaskMediaEntity {
    return TaskMediaEntity(
        taskId = taskId,
        mediaId = media.id,
        createdAt = createdAt
    )
}