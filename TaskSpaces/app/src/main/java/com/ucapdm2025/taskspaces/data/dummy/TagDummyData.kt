package com.ucapdm2025.taskspaces.data.dummy

import com.ucapdm2025.taskspaces.data.model.Tag

val tagsDummy: List<Tag> = listOf(
    Tag(
        id = 1,
        title = "Tag 1",
        color = "#FF5733",
        projectId = 1,
        createdAt = "2023-10-01T12:00:00Z",
        updatedAt = "2023-10-01T12:00:00Z"
    ),
    Tag(
        id = 2,
        title = "Tag 2",
        color = "#33FF57",
        projectId = 2,
        createdAt = "2023-10-02T12:00:00Z",
        updatedAt = "2023-10-02T12:00:00Z"
    ),
    Tag(
        id = 3,
        title = "Tag 3",
        color = "#3357FF",
        projectId = 3,
        createdAt = "2023-10-03T12:00:00Z",
        updatedAt = "2023-10-03T12:00:00Z"
    )
)