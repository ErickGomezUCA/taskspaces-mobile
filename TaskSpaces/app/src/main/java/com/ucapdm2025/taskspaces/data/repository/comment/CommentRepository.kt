package com.ucapdm2025.taskspaces.data.repository.comment

import com.ucapdm2025.taskspaces.data.model.Comment
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
    fun getCommentsByTaskId(taskId: Int): Flow<List<Comment>>
    suspend fun createComment(content: String, authorId: Int, taskId: Int): Comment
    suspend fun updateComment(id: Int, content: String, authorId: Int, taskId: Int): Comment
    suspend fun deleteComment(id: Int): Boolean
}