package com.ucapdm2025.taskspaces.data.repository.comment

import com.ucapdm2025.taskspaces.data.dummy.commentsDummy
import com.ucapdm2025.taskspaces.data.model.Comment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime

class CommentRepositoryImpl: CommentRepository {
    private val comments = MutableStateFlow(commentsDummy)

    private var autoIncrementId = comments.value.size + 1

    override fun getCommentsByTaskId(taskId: Int): Flow<List<Comment>> {
        return comments.map { list -> list.filter { it.taskId == taskId } }
    }

    override suspend fun createComment(content: String, authorId: Int, taskId: Int): Comment {
        val createdComment = Comment(
            id = autoIncrementId++,
            content = content,
            authorId = authorId,
            taskId = taskId,
            createdAt = LocalDateTime.now().toString(),
            updatedAt = LocalDateTime.now().toString()
        )

        comments.value = comments.value + createdComment

        return createdComment
    }

    override suspend fun updateComment(id: Int, content: String, authorId: Int, taskId: Int): Comment {
        val updatedComment = Comment(
            id = id,
            content = content,
            authorId = authorId,
            taskId = taskId,
            createdAt = LocalDateTime.now().toString(),
            updatedAt = LocalDateTime.now().toString()
        )

        comments.value = comments.value.map {
            if (it.id == updatedComment.id) updatedComment else it
        }

        return updatedComment
    }

    override suspend fun deleteComment(id: Int): Boolean {
        val exists = comments.value.any { it.id == id }

        if (exists) {
            comments.value = comments.value.filter { it.id != id }
        }

        return exists
    }
}