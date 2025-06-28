package com.ucapdm2025.taskspaces.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ucapdm2025.taskspaces.data.model.CommentModel
import com.ucapdm2025.taskspaces.data.model.UserModel

/**
 * CommentEntity is a data class that represents a comment in the database.
 *
 * @property id The unique identifier for the comment.
 * @property content The content of the comment.
 * @property authorId The ID of the user who authored the comment.
 * @property taskId The ID of the task to which the comment belongs.
 * @property edited Indicates whether the comment has been edited.
 * @property createdAt The timestamp when the comment was created.
 * @property updatedAt The timestamp when the comment was last updated.
 */
@Entity(tableName = "comment")
data class CommentEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Int,
    val content: String,
    val authorId: Int,
    val taskId: Int,
    val edited: Boolean = false,
    override val createdAt: String? = null,
    override val updatedAt: String? = null,
): BaseEntity(id, createdAt, updatedAt)

/**
 * Extension function to convert a CommentEntity to a CommentModel.
 *
 * @return A CommentModel instance with the same properties as the CommentEntity.
 */
fun CommentEntity.toDomain(author: UserModel): CommentModel {
    return CommentModel(
        id = id,
        content = content,
        authorId = authorId,
        author = author,
        taskId = taskId,
        edited = edited,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}