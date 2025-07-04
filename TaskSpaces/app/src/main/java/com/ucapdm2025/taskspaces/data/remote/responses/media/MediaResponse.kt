package com.ucapdm2025.taskspaces.data.remote.responses.media

import com.ucapdm2025.taskspaces.data.database.entities.MediaEntity
import com.ucapdm2025.taskspaces.data.model.MediaModel

/**
 * TaskMediaResponse represents the relationship between a task and its associated media files in the API response.
 *
 * @property id The unique identifier for the media file.
 * @property filename The name of the media file.
 * @property type The type of the media file (e.g., image, video).
 * @property url The URL where the media file is stored.
 * @property createdAt The timestamp when the media file was created.
 * @property updatedAt The timestamp when the media file was last updated.
 */
data class MediaResponse(
    val id: Int,
    val filename: String,
    val type: String,
    val url: String,
    val createdAt: String? = null,
    val updatedAt: String? = null
    )

/**
 * Converts MediaResponse to MediaModel for domain use.
 *
 * @return MediaModel representing the media file in the domain layer.
 */
fun MediaResponse.toDomain(): MediaModel {
    return MediaModel(
        id = id,
        filename = filename,
        type = type,
        url = url,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

/**
 * Converts MediaResponse to MediaEntity for database storage.
 *
 * @return MediaEntity representing the media file in the database.
 */
fun MediaResponse.toDatabase(): MediaEntity {
    return MediaEntity(
        id = id,
        filename = filename,
        type = type,
        url = url,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}