package com.ucapdm2025.taskspaces.data.repository.comment

import com.ucapdm2025.taskspaces.data.dummy.commentsDummies
import com.ucapdm2025.taskspaces.data.model.CommentModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime

/**
 * CommentRepositoryImpl is an implementation of the CommentRepository interface.
 * It provides methods to manage comments in the application, including retrieving,
 * creating, updating, and deleting comments.
 */
class CommentRepositoryImpl: CommentRepository {
    private val comments = MutableStateFlow(commentsDummies)

    private var autoIncrementId = comments.value.size + 1

    override fun getCommentsByTaskId(taskId: Int): Flow<List<CommentModel>> {
        return comments.map { list -> list.filter { it.taskId == taskId } }
    }

    override suspend fun createComment(content: String, authorId: Int, taskId: Int): CommentModel {
        val createdCommentModel = CommentModel(
            id = autoIncrementId++,
            content = content,
            authorId = authorId,
            taskId = taskId,
            createdAt = LocalDateTime.now().toString(),
            updatedAt = LocalDateTime.now().toString()
        )

        comments.value = comments.value + createdCommentModel

        return createdCommentModel
    }

    override suspend fun updateComment(id: Int, content: String, authorId: Int, taskId: Int): CommentModel {
        val updatedCommentModel = CommentModel(
            id = id,
            content = content,
            authorId = authorId,
            taskId = taskId,
            createdAt = LocalDateTime.now().toString(),
            updatedAt = LocalDateTime.now().toString()
        )

        comments.value = comments.value.map {
            if (it.id == updatedCommentModel.id) updatedCommentModel else it
        }

        return updatedCommentModel
    }

    override suspend fun deleteComment(id: Int): Boolean {
        val exists = comments.value.any { it.id == id }

        if (exists) {
            comments.value = comments.value.filter { it.id != id }
        }

        return exists
    }
}