package com.ucapdm2025.taskspaces.data.remote.responses

import com.ucapdm2025.taskspaces.data.database.entities.CommentEntity
import com.ucapdm2025.taskspaces.data.model.CommentModel

/**
 * CommentResponse is a data class that represents the response body for a comment.
 *
 * @property id The unique identifier for the comment.
 * @property content The content of the comment.
 * @property taskId The ID of the task to which the comment belongs.
 * @property authorId The ID of the user who authored the comment.
 * @property author The user who authored the comment, represented as a UserResponse.
 * @property edited Indicates whether the comment has been edited.
 * @property createdAt The timestamp when the comment was created, represented as a String.
 * @property updatedAt The timestamp when the comment was last updated, represented as a String.
 */
data class CommentResponse(
    val id: Int,
    val content: String,
    val taskId: Int,
    val authorId: Int,
    val author: UserResponse,
    val edited: Boolean = false,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

/**
 * Converts CommentResponse to CommentModel for use in the application.
 *
 * @return CommentModel representing the comment in the application.
 */
fun CommentResponse.toDomain(): CommentModel {
    return CommentModel(
        id = id,
        content = content,
        authorId = authorId,
        taskId = taskId,
        edited = edited,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

/**
 * Converts CommentResponse to CommentEntity for database storage.
 *
 * @return CommentEntity representing the comment in the database.
 */
fun CommentResponse.toEntity(): CommentEntity {
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