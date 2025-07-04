package com.ucapdm2025.taskspaces.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ucapdm2025.taskspaces.data.model.MediaModel

/**
 * MediaEntity is a data class that represents a media file in the database.
 *
 * @param id The unique identifier for the media file.
 * @param filename The name of the media file.
 * @param type The type of the media file (e.g., image, video).
 * @param url The URL where the media file is stored.
 * @param createdAt The timestamp when the media file was created.
 * @param updatedAt The timestamp when the media file was last updated.
 */
@Entity(tableName = "media")
data class MediaEntity (
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    val filename: String,
    val type: String,
    val url: String,
    override val createdAt: String? = null,
    override val updatedAt: String? = null
): BaseEntity(id, createdAt, updatedAt)

/**
 * Extension function to convert a MediaEntity to a MediaModel.
 *
 * @return A MediaModel instance with the same properties as the MediaEntity.
 */

fun MediaEntity.toDomain(): MediaModel {
    return MediaModel(
        id = id,
        filename = filename,
        type = type,
        url = url,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}