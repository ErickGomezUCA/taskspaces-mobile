package com.ucapdm2025.taskspaces.data.remote.responses

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.toColorInt
import com.ucapdm2025.taskspaces.data.database.entities.TagEntity
import com.ucapdm2025.taskspaces.data.model.TagModel
import com.ucapdm2025.taskspaces.utils.toColorRRGGBBAA

/**
 * Data class representing a response containing tag information.
 *
 * @property id The unique identifier of the tag.
 * @property title The title of the tag.
 * @property color The color of the tag, represented as a string.
 * @property createdAt The timestamp when the tag was created.
 * @property updatedAt The timestamp when the tag was last updated.
 */
data class TagResponse (
    val id: Int,
    val title: String,
    val color: String,
    val projectId: Int,
    val createdAt: String,
    val updatedAt: String
)

/**
 * Converts TagResponse to TagModel for application use.
 *
 * @return TagModel representing the tag in the application.
 */
fun TagResponse.toDomain(): TagModel {
    return TagModel(
        id = id,
        title = title,
        color = color.toColorRRGGBBAA(),
        projectId = projectId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

/**
 * Converts TagResponse to TagModel for database storage.
 *
 * @return TagModel representing the tag in the database.
 */
fun TagResponse.toEntity(): TagEntity {
    return TagEntity(
        id = id,
        title = title,
        color = color,
        projectId = projectId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}