package com.ucapdm2025.taskspaces.data.repository.bookmark

import com.ucapdm2025.taskspaces.data.model.BookmarkModel
import com.ucapdm2025.taskspaces.data.model.TaskModel
import com.ucapdm2025.taskspaces.helpers.Resource
import kotlinx.coroutines.flow.Flow

/**
 * BookmarkRepository is an interface that defines the methods for managing bookmarks in the application.
 */
interface BookmarkRepository {
    //    Fetch functions return a TaskModel, because bookmarks do not have the whole
//    information of a task, only the user ID and task ID.
    fun getUserBookmarks(): Flow<Resource<List<TaskModel>>>
    fun getBookmarkByTaskId(taskId: Int): Flow<Resource<TaskModel?>>
    suspend fun createBookmark(taskId: Int): Result<BookmarkModel>
    suspend fun deleteBookmark(taskId: Int): Result<BookmarkModel>
}