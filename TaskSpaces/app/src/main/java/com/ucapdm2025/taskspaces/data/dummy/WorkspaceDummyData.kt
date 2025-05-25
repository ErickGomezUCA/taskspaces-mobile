package com.ucapdm2025.taskspaces.data.dummy

import com.ucapdm2025.taskspaces.data.model.Workspace

val workspacesDummy: List<Workspace> = listOf(
    Workspace(
        id = 1,
        title = "Workspace 1",
        createdAt = "2023-10-01T12:00:00Z",
        updatedAt = "2023-10-01T12:00:00Z"
    ),
    Workspace(
        id = 2,
        title = "Workspace 2",
        createdAt = "2023-10-02T12:00:00Z",
        updatedAt = "2023-10-02T12:00:00Z"
    ),
    Workspace(
        id = 3,
        title = "Workspace 3",
        createdAt = "2023-10-03T12:00:00Z",
        updatedAt = "2023-10-03T12:00:00Z"
    )
)

val workspacesSharedDummy = listOf<Workspace>(
    Workspace(
        id = 4,
        title = "Workspace 4",
        createdAt = "2023-10-04T12:00:00Z",
        updatedAt = "2023-10-04T12:00:00Z"
    ),
    Workspace(
        id = 5,
        title = "Workspace 5",
        createdAt = "2023-10-05T12:00:00Z",
        updatedAt = "2023-10-05T12:00:00Z"
    ),
    Workspace(
        id = 6,
        title = "Workspace 6",
        createdAt = "2023-10-06T12:00:00Z",
        updatedAt = "2023-10-06T12:00:00Z"
    )
)