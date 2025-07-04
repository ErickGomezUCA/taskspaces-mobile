package com.ucapdm2025.taskspaces.data.repository.bookmark

import android.util.Log
import coil3.network.HttpException
import com.ucapdm2025.taskspaces.data.database.dao.relational.BookmarkDao
import com.ucapdm2025.taskspaces.data.database.dao.TaskDao
import com.ucapdm2025.taskspaces.data.database.entities.relational.toDomain
import com.ucapdm2025.taskspaces.data.database.entities.toDomain
import com.ucapdm2025.taskspaces.data.model.relational.BookmarkModel
import com.ucapdm2025.taskspaces.data.model.TaskModel
import com.ucapdm2025.taskspaces.data.model.relational.toDatabase
import com.ucapdm2025.taskspaces.data.remote.responses.BookmarkResponse
import com.ucapdm2025.taskspaces.data.remote.responses.toDomain
import com.ucapdm2025.taskspaces.data.remote.responses.toEntity
import com.ucapdm2025.taskspaces.data.remote.responses.workspace.toDomain
import com.ucapdm2025.taskspaces.data.remote.responses.workspace.toEntity
import com.ucapdm2025.taskspaces.data.remote.services.BookmarkService
import com.ucapdm2025.taskspaces.data.repository.auth.AuthRepository
import com.ucapdm2025.taskspaces.helpers.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okio.IOException

/**
 * BookmarkRepositoryImpl is an implementation of the BookmarkRepository interface.
 * It provides methods to manage bookmarks in the application, including retrieving,
 * creating, and deleting bookmarks.
 */
class BookmarkRepositoryImpl(
    private val authRepository: AuthRepository,
    private val bookmarkDao: BookmarkDao,
    private val bookmarkService: BookmarkService,
    private val taskDao: TaskDao
) : BookmarkRepository {
    override fun getUserBookmarks(): Flow<Resource<List<TaskModel>>> = flow {
        emit(Resource.Loading)

//        Used for fetching the user ID from the auth repository
//        Specially for dao functions, because those does not have access to
//        auth tokens
        val userId: Int = authRepository.authUserId.first()

        try {
            //            Fetch bookmarks from remote
            val remoteBookmarks: List<BookmarkResponse> =
                bookmarkService.getUserBookmarks().content

            Log.d("test1", remoteBookmarks.toString())

            //            Save remote bookmarks to the database
            if (remoteBookmarks.isNotEmpty()) {
                remoteBookmarks.forEach {
                    bookmarkDao.createBookmark(it.toEntity())
                }
            }
        } catch (e: Exception) {
            Log.d(
                "BookmarkRepository: getUserBookmarks",
                "Error fetching bookmarks: ${e.message}"
            )
        }

//        Use local bookmarks tasks
        val localBookmarkedTasks =
            bookmarkDao.getBookmarksByUserId(userId = userId).map { entities ->
                val bookmarks = entities.map { it.toDomain() }

                val localTasks = bookmarks.mapNotNull { bookmark ->
                    taskDao.getTaskById(bookmark.taskId).first()?.toDomain()
                }

//                Returns the tasks bookmarked as a success (to domain)
                Resource.Success(localTasks)
            }.distinctUntilChanged()

        emitAll(localBookmarkedTasks)
    }

    override fun getBookmarkByTaskId(taskId: Int): Flow<Resource<TaskModel?>> = flow {
        emit(Resource.Loading)

//        Used for fetching the user ID from the auth repository
//        Specially for dao functions, because those does not have access to
//        auth tokens
        val userId: Int = authRepository.authUserId.first()

        try {
            //            Fetch bookmarks from remote
            val remoteBookmarks: BookmarkResponse? =
                bookmarkService.getBookmarkByTaskId(taskId = taskId).content

            //            Save remote bookmarks to the database
            if (remoteBookmarks != null) {
                bookmarkDao.createBookmark(remoteBookmarks.toEntity())
            }
        } catch (e: Exception) {
            Log.d(
                "BookmarkRepository: getBookmarkByTaskId",
                "Error fetching bookmark: ${e.message}"
            )
        }

//        Use local bookmarks tasks
        val localBookmarkedTask =
            bookmarkDao.getBookmarkByUserIdAndTaskId(userId = userId, taskId = taskId)
                .map { entity ->
                    val bookmark = entity?.toDomain()

                    if (bookmark == null) {
                        //                Logs an error if no projects are found for the user
                        Resource.Error("No bookmark found for user with ID: $userId")
                    } else {
//                    Convert bookmarks into tasks
//                    Because bookmarks only have userId and taskId as their columns, but to obtain
//                    the task information, we need to convert them into TaskModel
                        val localTask = taskDao.getTaskById(bookmark.taskId).first()?.toDomain()

//                Returns the tasks bookmarked as a success (to domain)
                        Resource.Success(localTask)
                    }
                }.distinctUntilChanged()

        emitAll(localBookmarkedTask)
    }

    override suspend fun isBookmarked(taskId: Int): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading)

        val userId: Int = authRepository.authUserId.first()

        val isBookmarked = bookmarkDao.getBookmarkByUserIdAndTaskId(userId = userId, taskId = taskId).map { entity ->
            val bookmark = entity?.toDomain()

            if (bookmark == null) {
                Resource.Success(false)
            } else {
                Resource.Success(true)
            }
        }.distinctUntilChanged()

        emitAll(isBookmarked)
    }

    override suspend fun createBookmark(taskId: Int): Result<BookmarkModel> {
        return try {
            val response = bookmarkService.createBookmark(taskId = taskId)

            val createdBookmark: BookmarkModel = response.content.toDomain()

//            Create retrieved bookmark from remote server into the local database
            bookmarkDao.createBookmark(createdBookmark.toDatabase())

            Log.d(
                "BookmarkRepository: createBookmark",
                "Creating bookmark for task ID: ${createdBookmark.taskId}"
            )

            Result.success(createdBookmark)
        } catch (e: HttpException) {
            Log.e("BookmarkRepository", "Create bookmark failed: ${e.message}", e)
            Result.failure(e)
        } catch (e: IOException) {
            Log.e("BookmarkRepository", "Network error creating bookmark: ${e.message}", e)
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("BookmarkRepository", "Unexpected error during create bookmark: ${e.message}", e)
            Result.failure(e)
        }
    }

    override suspend fun deleteBookmark(taskId: Int): Result<BookmarkModel> {
        return try {
            val response = bookmarkService.deleteBookmark(taskId = taskId)

            val deletedBookmark: BookmarkModel = response.content.toDomain()

            bookmarkDao.deleteBookmark(deletedBookmark.toDatabase())

            Log.d(
                "BookmarkRepository: deleteBookmark",
                "Deleting bookmark for task ID: ${deletedBookmark.taskId}"
            )

            Result.success(deletedBookmark)
        } catch (e: HttpException) {
            Log.e("BookmarkRepository", "Delete bookmark failed: ${e.message}", e)
            Result.failure(e)
        } catch (e: IOException) {
            Log.e("BookmarkRepository", "Network error deleting bookmark: ${e.message}", e)
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("BookmarkRepository", "Unexpected error during delete bookmark: ${e.message}", e)
            Result.failure(e)
        }
    }
}