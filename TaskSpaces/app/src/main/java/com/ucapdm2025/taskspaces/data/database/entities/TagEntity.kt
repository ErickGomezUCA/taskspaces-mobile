package com.ucapdm2025.taskspaces.data.database.entities

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ucapdm2025.taskspaces.data.model.TagModel
import androidx.core.graphics.toColorInt

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
 * Extension function to convert a TagEntity to a TagModel.
 *
 * @return A TagModel instance with the same properties as the TagEntity.
 */
fun TagEntity.toDomain(): TagModel {
    return TagModel(
        id = id,
        title = title,
        color = Color(color.toColorInt()),
        projectId = projectId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}