package com.ucapdm2025.taskspaces.data.repository.comment

import android.util.Log
import coil3.network.HttpException
import com.ucapdm2025.taskspaces.data.database.dao.CommentDao
import com.ucapdm2025.taskspaces.data.database.dao.UserDao
import com.ucapdm2025.taskspaces.data.database.entities.toDomain
import com.ucapdm2025.taskspaces.data.model.CommentModel
import com.ucapdm2025.taskspaces.data.model.UserModel
import com.ucapdm2025.taskspaces.data.model.toDatabase
import com.ucapdm2025.taskspaces.data.remote.requests.CommentRequest
import com.ucapdm2025.taskspaces.data.remote.responses.CommentResponse
import com.ucapdm2025.taskspaces.data.remote.responses.toDomain
import com.ucapdm2025.taskspaces.data.remote.responses.toEntity
import com.ucapdm2025.taskspaces.data.remote.services.CommentService
import com.ucapdm2025.taskspaces.helpers.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okio.IOException
import kotlin.collections.forEach
import kotlin.collections.map

/**
 * CommentRepositoryImpl is an implementation of the CommentRepository interface.
 * It provides methods to manage comments in the application, including retrieving,
 * creating, updating, and deleting comments.
 */
class CommentRepositoryImpl(
    private val commentDao: CommentDao,
    private val userDao: UserDao,
    private val commentService: CommentService
): CommentRepository {
    override fun getCommentsByTaskId(taskId: Int): Flow<Resource<List<CommentModel>>> = flow {
        emit(Resource.Loading)

        try {
            val remoteComments: List<CommentResponse> =
                commentService.getCommentsByTaskId(taskId = taskId).content

            if (remoteComments.isNotEmpty()) {
                remoteComments.forEach {
                    commentDao.createComment(it.toEntity())
                }
            }
        } catch (e: Exception) {
            Log.d(
                "CommentRepository: getCommentsByTaskId",
                "Error fetching comments: ${e.message}"
            )
        }

        //        Use local comments
        val localComments =
            commentDao.getCommentsByTaskId(taskId = taskId).map { entities ->
                val comments = entities.map { entity ->
                    val author: UserModel? = userDao.getUserById(entity.authorId).first()?.toDomain()
                    entity.toDomain(author ?: UserModel(
                        id = 0,
                        username = "Unknown",
                        fullname = "Unknown",
                        email = "Unknown",
                    ))
                }

                if (comments.isEmpty()) {
                    //                Logs an error if no comments are found for the user
                    Resource.Error("No comments found for task with ID: $taskId")
                } else {
                    //                Returns the comments as a success (to domain)
                    Resource.Success(comments)
                }
            }.distinctUntilChanged()

        emitAll(localComments)
    }

    override suspend fun createComment(content: String, taskId: Int): Result<CommentModel> {
        val request = CommentRequest(content)

        return try {
            val response =
                commentService.createComment(taskId = taskId, request = request)

            val createdComment: CommentModel = response.content.toDomain()

//            Create retrieved comment from remote server into the local database
            commentDao.createComment(createdComment.toDatabase())

            Log.d(
                "CommentRepository: createComment",
                "Comment created successfully: $createdComment"
            )

            Result.success(createdComment)
        } catch (e: HttpException) {
            Log.e("CommentRepository", "Error creating comment: ${e.message}")
            Result.failure(e)
        } catch (e: IOException) {
            Log.e("CommentRepository", "Network error creating comment: ${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("CommentRepository", "Unexpected error creating comment: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun updateComment(id: Int, content: String): Result<CommentModel> {
        val request = CommentRequest(content)

        return try {
            val response =
                commentService.updateComment(commentId = id, request = request)

            val updatedComment: CommentModel = response.content.toDomain()

//            Update retrieved comment from remote server into the local database
            commentDao.updateComment(updatedComment.toDatabase())

            Log.d(
                "CommentRepository: updateComment",
                "Comment updated successfully: $updatedComment"
            )

            Result.success(updatedComment)
        } catch (e: HttpException) {
            Log.e("CommentRepository", "Error updating comment: ${e.message}")
            Result.failure(e)
        } catch (e: IOException) {
            Log.e("CommentRepository", "Network error updating comment: ${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("CommentRepository", "Unexpected error updating comment: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun deleteComment(id: Int): Result<CommentModel> {
        return try {
            val response =
                commentService.deleteComment(commentId = id)

            val deletedComment: CommentModel = response.content.toDomain()

//            Delete retrieved comment from remote server into the local database
            commentDao.deleteComment(deletedComment.toDatabase())

            Log.d(
                "CommentRepository: deleteComment",
                "Comment updated successfully: $deletedComment"
            )

            Result.success(deletedComment)
        } catch (e: HttpException) {
            Log.e("CommentRepository", "Error deleting comment: ${e.message}")
            Result.failure(e)
        } catch (e: IOException) {
            Log.e("CommentRepository", "Network error deleting comment: ${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("CommentRepository", "Unexpected error deleting comment: ${e.message}")
            Result.failure(e)
        }
    }
}