package com.ucapdm2025.taskspaces.data.model.relational

import com.ucapdm2025.taskspaces.data.database.entities.relational.TaskAssignedEntity

/**
 * TaskAssignedModel is a data class that represents a many-to-many relationship between tasks and users.
 *
 * @property userId The ID of the user assigned to the task.
 * @property taskId The ID of the task assigned to the user.
 * @property createdAt The timestamp when the task was assigned to the user, represented as a String.
 */
data class TaskAssignedModel (
    val userId: Int,
    val taskId: Int,
    val createdAt: String? = null
)

/**
 * Converts TaskAssignedModel to TaskAssignedEntity for database representation.
 */
fun TaskAssignedModel.toDatabase(): TaskAssignedEntity {
    return TaskAssignedEntity(
        userId = userId,
        taskId = taskId,
        createdAt = createdAt
    )
}