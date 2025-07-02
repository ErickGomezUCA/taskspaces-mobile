package com.ucapdm2025.taskspaces.data.model

import com.ucapdm2025.taskspaces.data.database.entities.CommentEntity

/**
 * CommentModel represents a comment in the system.
 * It extends BaseModel to include common properties
 * like id, createdAt, and updatedAt.
 *
 * @property id The unique identifier for the comment.
 * @property content The content of the comment.
 * @property authorId The ID of the user who authored the comment.
 * @property taskId The ID of the task to which the comment belongs.
 * @property createdAt The timestamp when the comment was created.
 * @property updatedAt The timestamp when the comment was last updated.
 */
data class CommentModel(
    override val id: Int,
    val content: String,
    val authorId: Int,
    val author: UserModel,
    val taskId: Int,
    val edited: Boolean = false,
    override val createdAt: String? = null,
    override val updatedAt: String? = null,
): BaseModel(id, createdAt, updatedAt)

/**
 * Converts CommentModel to CommentEntity for database storage.
 *
 * @return CommentEntity representing the comment in the database.
 */
fun CommentModel.toDatabase(): CommentEntity {
    return CommentEntity(
        id = id,
        content = content,
        authorId = authorId,
        taskId = taskId,
        edited = edited,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}