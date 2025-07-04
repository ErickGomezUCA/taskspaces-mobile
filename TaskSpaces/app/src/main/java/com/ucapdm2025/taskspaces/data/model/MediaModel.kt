package com.ucapdm2025.taskspaces.data.model

import com.ucapdm2025.taskspaces.data.database.entities.MediaEntity

/**
 * BaseModel is an abstract class that represents a base model
 *
 * @property id The unique identifier for the model.
 * @property filename The name of the media file.
 * @property type The type of the media file (e.g., image, video).
 * @property url The URL where the media file is stored.
 * @property createdAt The timestamp when the model was created.
 * @property updatedAt The timestamp when the model was last updated.
 */
data class MediaModel(
    override val id: Int,
    val filename: String,
    val type: String,
    val url: String,
    override val createdAt: String? = null,
    override val updatedAt: String? = null
) : BaseModel(id, createdAt, updatedAt)

/**
 * Converts MediaModel to MediaEntity for database storage.
 *
 * @return MediaEntity representing the media file in the database.
 */
fun MediaModel.toDatabase(): MediaEntity {
    return MediaEntity(
        id = id,
        filename = filename,
        type = type,
        url = url,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}