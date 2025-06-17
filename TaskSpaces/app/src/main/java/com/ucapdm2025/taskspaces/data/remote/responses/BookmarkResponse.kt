package com.ucapdm2025.taskspaces.data.remote.responses

import com.ucapdm2025.taskspaces.data.database.entities.BookmarkEntity
import com.ucapdm2025.taskspaces.data.model.BookmarkModel

/**
 * BookmarkResponse is a data class that represents the response for a bookmark operation.
 *
 * @property userId The ID of the user who created the bookmark.
 * @property taskId The ID of the task that is bookmarked.
 * @property createdAt The timestamp when the bookmark was created, represented as a String.
 */
data class BookmarkResponse(
    val userId: Int,
    val taskId: Int,
    val createdAt: String? = null
)

/**
 * Converts BookmarkResponse to BookmarkModel for application use.
 *
 * @return BookmarkModel representing the bookmark in the application.
 */
fun BookmarkResponse.toDomain(): BookmarkModel {
    return BookmarkModel(
        userId = userId,
        taskId = taskId,
        createdAt = createdAt
    )
}

/**
 * Converts BookmarkResponse to BookmarkEntity for database storage.
 *
 * @return BookmarkEntity representing the bookmark in the database.
 */
fun BookmarkResponse.toEntity(): BookmarkEntity {
    return BookmarkEntity(
        userId = userId,
        taskId = taskId,
        createdAt = createdAt
    )
}