package com.ucapdm2025.taskspaces.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ucapdm2025.taskspaces.data.model.BaseModel
import com.ucapdm2025.taskspaces.data.model.TagModel
import com.ucapdm2025.taskspaces.data.model.TaskModel
import com.ucapdm2025.taskspaces.ui.components.projects.StatusVariations
import com.ucapdm2025.taskspaces.utils.toLocalDateTime
import java.time.LocalDateTime

// Tags, assignedMembers and comments should be handled in separate entities\
/**
 * TaskEntity is a data class that represents a task in the database.
 *
 * @property id The unique identifier for the task.
 * @property breadcrumb The breadcrumb path for the task, default is "/".
 * @property title The title of the task.
 * @property description An optional description of the task.
 * @property deadline An optional deadline for the task, represented as a String (TODO: should be LocalDateTime).
 * @property timer An optional timer for the task, represented as a Float.
 * @property status The status of the task, represented by StatusVariations.
 * @property projectId The ID of the project to which the task belongs.
 * @property createdAt The timestamp when the task was created.
 * @property updatedAt The timestamp when the task was last updated.
 */
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
): BaseEntity(id, createdAt, updatedAt)

/**
 * Extension function to convert a TaskEntity to a TaskModel.
 *
 * @return A TaskModel instance with the same properties as the TaskEntity.
 */
fun TaskEntity.toDomain(tags: List<TagModel> = emptyList< TagModel>()): TaskModel {
    return TaskModel(
        id = id,
        breadcrumb = breadcrumb,
        title = title,
        description = description,
        deadline = deadline?.toLocalDateTime(),
        timer = timer,
        status = when (status.uppercase()) {
            "PENDING" -> StatusVariations.PENDING
            "DOING" -> StatusVariations.DOING
            "DONE" -> StatusVariations.DONE
            else -> StatusVariations.PENDING // Default case
        },
        projectId = projectId,
        tags = tags,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}