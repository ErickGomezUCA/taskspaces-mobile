package com.ucapdm2025.taskspaces.data.model

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
    val taskId: Int,
    override val createdAt: String = "",
    override val updatedAt: String = "",
): BaseModel(id, createdAt, updatedAt)