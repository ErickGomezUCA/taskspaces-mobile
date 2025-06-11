package com.ucapdm2025.taskspaces.data.model

import androidx.compose.ui.graphics.Color

data class TagModel (
    override val id: Int,
    val title: String,
    val color: Color,
    val projectId: Int,
    override val createdAt: String,
    override val updatedAt: String,
): BaseModel(id, createdAt, updatedAt)