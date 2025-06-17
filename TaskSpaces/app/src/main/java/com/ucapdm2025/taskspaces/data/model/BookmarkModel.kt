package com.ucapdm2025.taskspaces.data.model

import com.ucapdm2025.taskspaces.data.database.entities.BookmarkEntity

/**
 * BookmarkModel represents a bookmark for a task in the application.
 *
 * @property userId The ID of the user who created the bookmark.
 * @property taskId The ID of the task that is bookmarked.
 * @property createdAt The timestamp when the bookmark was created, represented as a String.
 */
data class BookmarkModel(
    val userId: Int,
    val taskId: Int,
    val createdAt: String? = null,
)

/**
 * Converts BookmarkEntity to BookmarkModel for domain representation.
 *
 * @return BookmarkModel representing the bookmark in the domain layer.
 */
fun BookmarkModel.toDatabase(): BookmarkEntity {
    return BookmarkEntity(
        userId = userId,
        taskId = taskId,
        createdAt = createdAt
    )
}