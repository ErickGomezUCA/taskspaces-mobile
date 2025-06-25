package com.ucapdm2025.taskspaces.data.dummy

import com.ucapdm2025.taskspaces.data.model.WorkspaceModel

val workspacesSharedDummies = listOf<WorkspaceModel>(
    WorkspaceModel(
        id = 4,
        title = "Workspace 4",
        ownerId = 2,
        createdAt = "2023-10-04T12:00:00Z",
        updatedAt = "2023-10-04T12:00:00Z"
    ),
    WorkspaceModel(
        id = 5,
        title = "Workspace 5",
        ownerId = 2,
        createdAt = "2023-10-05T12:00:00Z",
        updatedAt = "2023-10-05T12:00:00Z"
    ),
    WorkspaceModel(
        id = 6,
        title = "Workspace 6",
        ownerId = 1,
        createdAt = "2023-10-06T12:00:00Z",
        updatedAt = "2023-10-06T12:00:00Z"
    )
)