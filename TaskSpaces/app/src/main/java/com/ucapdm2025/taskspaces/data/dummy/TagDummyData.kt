package com.ucapdm2025.taskspaces.data.dummy

import androidx.compose.ui.graphics.Color
import com.ucapdm2025.taskspaces.data.model.TagModel

val tagsDummies: List<TagModel> = listOf(
    TagModel(
        id = 1,
        title = "Tag 1",
        color = Color(0xFFFF5733),
        projectId = 1,
        createdAt = "2023-10-01T12:00:00Z",
        updatedAt = "2023-10-01T12:00:00Z"
    ),
    TagModel(
        id = 2,
        title = "Tag 2",
        color = Color(0xFF33FF57),
        projectId = 2,
        createdAt = "2023-10-02T12:00:00Z",
        updatedAt = "2023-10-02T12:00:00Z"
    ),
    TagModel(
        id = 3,
        title = "Tag 3",
        color = Color(0xFF3357FF),
        projectId = 3,
        createdAt = "2023-10-03T12:00:00Z",
        updatedAt = "2023-10-03T12:00:00Z"
    )
)