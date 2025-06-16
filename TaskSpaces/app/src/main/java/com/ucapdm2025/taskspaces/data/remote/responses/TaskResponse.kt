package com.ucapdm2025.taskspaces.data.remote.responses

import com.ucapdm2025.taskspaces.data.database.entities.TaskEntity
import com.ucapdm2025.taskspaces.data.model.TaskModel
import com.ucapdm2025.taskspaces.ui.components.projects.StatusVariations
import java.time.LocalDateTime

/**
 * TaskResponse is a data class that represents the response from a task-related API call.
 * It contains details about a task, including its ID, title, description, status, deadline, timer,
 * project ID, and timestamps for creation and updates.
 *
 * @property id The unique identifier for the task.
 * @property title The title of the task.
 * @property description An optional description of the task.
 * @property status The status of the task, represented as a string (e.g., "PENDING", "DOING", "DONE").
 * @property deadline An optional deadline for the task, represented as a String (TODO: should be LocalDateTime).
 * @property timer An optional timer for the task, represented as a Float.
 * @property projectId The ID of the project to which the task belongs.
 * @property tags A list of tags associated with the task (TODO: handle in a separate entity).
 * @property assignedMembers A list of members assigned to the task (TODO: handle in a separate entity).
 * @property comments A list of comments on the task (TODO: handle in a separate entity).
 * @property createdAt The timestamp when the task was created.
 * @property updatedAt The timestamp when the task was last updated.
 */
data class TaskResponse(
    val id: Int,
    val title: String,
    val description: String? = null,
    val status: String, // Assuming status is a string, could be an enum or similar
    val deadline: String? = null, // TODO: Use LocalDateTime instead of String
    val timer: Float? = null,
    val projectId: Int,
    val tags: List<Any> = emptyList(), // TODO: Handle tags in a separate entity
    val assignedMembers: List<Any> = emptyList(), // TODO: Handle assigned members in a separate entity
    val comments: List<Any> = emptyList(), // TODO: Handle comments in a separate entity
    val createdAt: String? = null,
    val updatedAt: String? = null
)

/**
 * Converts TaskResponse to TaskModel for application use.
 *
 * @return TaskModel representing the task in the application.
 */
fun TaskResponse.toDomain(): TaskModel {
    return TaskModel(
        id = id,
        title = title,
        description = description,
        status = when (status.uppercase()) {
            "PENDING" -> StatusVariations.PENDING
            "DOING" -> StatusVariations.DOING
            "DONE" -> StatusVariations.DONE
            else -> StatusVariations.PENDING // Default case
        },
        deadline = deadline, // TODO: Parse correctly into LocalDateTime
        timer = timer,
        projectId = projectId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

/**
 * Converts TaskResponse to TaskModel for database storage.
 *
 * @return TaskModel representing the task in the database.
 */
fun TaskResponse.toEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        title = title,
        description = description,
        status = status.uppercase(),
        deadline = deadline, // TODO: Parse correctly into LocalDateTime
        timer = timer,
        projectId = projectId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}