package com.ucapdm2025.taskspaces.data.database.entities.relational

import androidx.room.Entity
import com.ucapdm2025.taskspaces.data.model.relational.TaskMediaModel

/**
 * TaskMediaEntity represents the relationship between a task and its associated media files in the database.
 *
 * @param taskId The unique identifier for the task.
 * @param mediaId The unique identifier for the media file.
 * @param createdAt The timestamp when the media was associated with the task.
 */
@Entity(tableName = "task_media", primaryKeys = ["taskId", "mediaId"])
data class TaskMediaEntity(
    val taskId: Int,
    val mediaId: Int,
    val createdAt: String? = null,
)

/**
 * Converts TaskMediaEntity to TaskMediaModel for domain representation.
 *
 * @return TaskMediaModel representing the relationship in the domain layer.
 */
fun TaskMediaEntity.toDomain(): TaskMediaModel {
    return TaskMediaModel(
        taskId = taskId,
        mediaId = mediaId,
        createdAt = createdAt
    )
}