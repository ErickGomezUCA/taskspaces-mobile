package com.ucapdm2025.taskspaces.data.repository.comment

import com.ucapdm2025.taskspaces.data.model.CommentModel
import com.ucapdm2025.taskspaces.helpers.Resource
import kotlinx.coroutines.flow.Flow

/**
 * CommentRepository is an interface that defines the methods for managing comments in the application.
 * It provides methods to get comments by task ID, create a new comment, update an existing comment,
 * and delete a comment.
 */
interface CommentRepository {
    fun getCommentsByTaskId(taskId: Int): Flow<Resource<List<CommentModel>>>
    suspend fun createComment(content: String, taskId: Int): Result<CommentModel>
    suspend fun updateComment(id: Int, content: String, taskId: Int): Result<CommentModel>
    suspend fun deleteComment(id: Int): Result<CommentModel>
}