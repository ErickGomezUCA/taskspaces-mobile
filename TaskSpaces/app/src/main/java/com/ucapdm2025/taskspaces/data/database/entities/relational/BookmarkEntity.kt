package com.ucapdm2025.taskspaces.data.database.entities.relational

import androidx.room.Entity
import com.ucapdm2025.taskspaces.data.model.relational.BookmarkModel

/**
 * BookmarkEntity is a data class that represents a bookmark for a task in the database.
 *
 * @property userId The ID of the user who created the bookmark.
 * @property taskId The ID of the task that is bookmarked.
 * @property createdAt The timestamp when the bookmark was created, represented as a String.
 */
@Entity(tableName = "bookmark", primaryKeys = ["userId", "taskId"])
data class BookmarkEntity(
    val userId: Int,
    val taskId: Int,
    val createdAt: String? = null
)

/**
 * Converts BookmarkModel to BookmarkEntity for database storage.
 *
 * @return BookmarkEntity representing the bookmark in the database.
 */
fun BookmarkEntity.toDomain(): BookmarkModel {
    return BookmarkModel(
        userId = userId,
        taskId = taskId,
        createdAt = createdAt
    )
}