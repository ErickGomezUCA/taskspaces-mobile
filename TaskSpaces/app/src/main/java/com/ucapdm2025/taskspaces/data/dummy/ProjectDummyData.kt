package com.ucapdm2025.taskspaces.data.dummy

import com.ucapdm2025.taskspaces.data.model.Project

val projectsDummy: List<Project> = listOf(
    Project(
        id = 1,
        title = "Project 1",
        icon = "https://example.com/icon1.png",
        createdAt = "2023-10-01T12:00:00Z",
        updatedAt = "2023-10-01T12:00:00Z"
    ),
    Project(
        id = 2,
        title = "Project 2",
        icon = "https://example.com/icon2.png",
        createdAt = "2023-10-02T12:00:00Z",
        updatedAt = "2023-10-02T12:00:00Z"
    ),
    Project(
        id = 3,
        title = "Project 3",
        icon = "https://example.com/icon3.png",
        createdAt = "2023-10-03T12:00:00Z",
        updatedAt = "2023-10-03T12:00:00Z"
    )
)