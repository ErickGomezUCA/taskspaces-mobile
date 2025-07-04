package com.ucapdm2025.taskspaces.data.repository.media

import com.ucapdm2025.taskspaces.data.model.MediaModel
import com.ucapdm2025.taskspaces.helpers.Resource
import kotlinx.coroutines.flow.Flow

/**
 * MediaRepository is an interface that defines the methods for managing media files in the application.
 * It provides methods to get media files by task ID, insert media files into a task, and delete media files.
 */
interface MediaRepository {
    fun getMediaByTaskId(taskId: Int): Flow<Resource<List<MediaModel>>>
    suspend fun insertMediaToTask(
        taskId: Int,
        filename: String,
        type: String,
        url: String,
    ): Result<MediaModel>
    suspend fun deleteMedia(id: Int): Result<MediaModel>
}