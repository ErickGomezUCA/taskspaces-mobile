package com.ucapdm2025.taskspaces.data.model

import androidx.compose.ui.graphics.Color

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
    override val createdAt: String = "",
    override val updatedAt: String = "",
): BaseModel(id, createdAt, updatedAt)