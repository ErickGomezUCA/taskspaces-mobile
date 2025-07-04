package com.ucapdm2025.taskspaces.data.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.ucapdm2025.taskspaces.data.database.entities.TagEntity

/**
 * TagModel represents a tag in the application, which can be associated with projects.
 *
 * @property id The unique identifier for the tag.
 * @property title The name of the tag.
 * @property color The color associated with the tag.
 * @property projectId The ID of the project to which this tag belongs.
 * @property createdAt The timestamp when the tag was created.
 * @property updatedAt The timestamp when the tag was last updated.
 */
data class TagModel (
    override val id: Int,
    val title: String,
    val color: Color,
    val projectId: Int,
    override val createdAt: String? = null,
    override val updatedAt: String? = null,
): BaseModel(id, createdAt, updatedAt)

/**
 * Converts TagModel to TagEntity for database storage.
 *
 * @return TagEntity representing the tag in the database.
 */
fun TagModel.toDatabase(): TagEntity {
    val r = (color.red * 255).toInt()
    val g = (color.green * 255).toInt()
    val b = (color.blue * 255).toInt()
    val a = (color.alpha * 255).toInt()
    val hexColor = String.format("#%02X%02X%02X%02X", r, g, b, a)

    return TagEntity(
        id = id,
        title = title,
        color = hexColor, // Convert Color to String representation
        projectId = projectId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}