package com.ucapdm2025.taskspaces.data.model

import com.ucapdm2025.taskspaces.data.database.entities.TaskEntity
import com.ucapdm2025.taskspaces.ui.components.projects.StatusVariations
import com.ucapdm2025.taskspaces.utils.toIsoString
import java.time.LocalDateTime

/**
 * TaskModel represents a task in the application, which can be associated with projects.
 *
 * @property id The unique identifier for the task.
 * @property breadcrumb A string representing the task's breadcrumb path.
 * @property title The title of the task.
 * @property description An optional description of the task.
 * @property deadline An optional deadline for the task, represented as a LocalDateTime object.
 * @property status The current status of the task, represented as a StatusVariations enum.
 * @property tags A list of tags associated with the task, represented as TagModel objects.
 * @property assignedMembers A list of users assigned to the task, represented as UserModel objects.
 * @property comments A list of comments associated with the task, represented as CommentModel objects.
 * @property projectId The ID of the project to which this task belongs.
 * @property createdAt The timestamp when the task was created, represented as a String.
 * @property updatedAt The timestamp when the task was last updated, represented as a String.
 */
data class TaskModel (
    override val id: Int,
    val breadcrumb: String = "/",
    val title: String = "New Task",
    val description: String? = null,
    val deadline: LocalDateTime? = null,
    val timer: Float? = null,
    val status: StatusVariations = StatusVariations.PENDING,
    val tags: List<TagModel> = emptyList(),
    val assignedMembers: List<UserModel> = emptyList(),
    val projectId: Int,
    override val createdAt: String? = null,
    override val updatedAt: String? = null,
): BaseModel(id, createdAt, updatedAt)

/**
 * Converts TaskModel to TaskEntity for database storage.
 *
 * @return TaskEntity representing the task in the database.
 */
fun TaskModel.toDatabase(): TaskEntity {
    return TaskEntity(
        id = id,
        breadcrumb = breadcrumb,
        title = title,
        description = description,
        deadline = deadline?.toIsoString(),
        timer = timer,
        status = status.name.uppercase(),
        projectId = projectId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}