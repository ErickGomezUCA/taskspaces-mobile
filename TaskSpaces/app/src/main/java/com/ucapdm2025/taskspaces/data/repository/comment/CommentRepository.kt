package com.ucapdm2025.taskspaces.data.repository.comment

import com.ucapdm2025.taskspaces.data.model.CommentModel
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
    fun getCommentsByTaskId(taskId: Int): Flow<List<CommentModel>>
    suspend fun createComment(content: String, authorId: Int, taskId: Int): CommentModel
    suspend fun updateComment(id: Int, content: String, authorId: Int, taskId: Int): CommentModel
    suspend fun deleteComment(id: Int): Boolean
}