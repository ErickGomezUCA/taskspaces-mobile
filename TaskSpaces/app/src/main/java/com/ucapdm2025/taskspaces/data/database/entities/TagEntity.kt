package com.ucapdm2025.taskspaces.data.database.entities

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ucapdm2025.taskspaces.data.model.TagModel

/**
 * TagEntity is a data class that represents a tag in the database.
 *
 * @property id The unique identifier for the tag.
 * @property title The title of the tag.
 * @property color The color associated with the tag.
 * @property projectId The ID of the project to which this tag belongs.
 * @property createdAt The timestamp when the tag was created, represented as a String.
 * @property updatedAt The timestamp when the tag was last updated, represented as a String.
 */
@Entity(tableName = "tag")
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    val title: String,
    val color: String,
    val projectId: Int,
    override val createdAt: String? = null,
    override val updatedAt: String? = null
) : BaseEntity(id, createdAt, updatedAt)

/**
 * Helper function to convert a #RRGGBBAA or #RRGGBB hex string to a Color instance.
 *
 * @return A Color instance corresponding to the hex string.
 */
private fun String.toColorRRGGBBAA(): Color {
    val hex = this.removePrefix("#")
    val fullHex = when (hex.length) {
        8 -> hex
        6 -> hex + "FF" // Add full alpha if not present
        else -> throw IllegalArgumentException("Color string must be in format #RRGGBBAA or #RRGGBB")
    }
    val r = fullHex.substring(0, 2).toInt(16)
    val g = fullHex.substring(2, 4).toInt(16)
    val b = fullHex.substring(4, 6).toInt(16)
    val a = fullHex.substring(6, 8).toInt(16)
    return Color(r, g, b, a)
}

/**
 * Extension function to convert a TagEntity to a TagModel.
 *
 * @return A TagModel instance with the same properties as the TagEntity.
 */
fun TagEntity.toDomain(): TagModel {
    return TagModel(
        id = id,
        title = title,
        color = color.toColorRRGGBBAA(),
        projectId = projectId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

