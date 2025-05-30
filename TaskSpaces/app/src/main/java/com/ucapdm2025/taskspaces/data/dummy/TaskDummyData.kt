package com.ucapdm2025.taskspaces.data.dummy

import com.ucapdm2025.taskspaces.data.model.Tag
import com.ucapdm2025.taskspaces.data.model.Task
import com.ucapdm2025.taskspaces.data.model.User

val tasksDummy: List<Task> = listOf(
    Task(
        id = 1,
        breadcrumb = "Workspace 1 / Project 1",
        title = "Task 1",
        description = "Description of Task 1",
        deadline = "2023-10-01",
        status = "In Progress",
        projectId = 1,
        createdAt = "2023-10-01T00:00:00Z",
        updatedAt = "2023-10-01T00:00:00Z"
    ),
    Task(
        id = 2,
        breadcrumb = "Workspace 2 / Project 2",
        title = "Task 2",
        description = "Description of Task 2",
        deadline = "2023-10-02",
        status = "Completed",
        projectId = 2,
        createdAt = "2023-10-02T00:00:00Z",
        updatedAt = "2023-10-02T00:00:00Z"
    )
)

val bookmarkedTasksDummy: List<Task> = listOf(
    Task(
        id = 3,
        breadcrumb = "Workspace 3 / Project 3",
        title = "Task 3",
        description = "Description of Task 3",
        deadline = "2023-10-01",
        status = "In Progress",
        projectId = 3,
        createdAt = "2023-10-01T00:00:00Z",
        updatedAt = "2023-10-01T00:00:00Z"
    ),
    Task(
        id = 4,
        breadcrumb = "Workspace 1 / Project 1",
        title = "Task 4",
        description = "Description of Task 4",
        deadline = "2023-10-01",
        status = "In Progress",
        projectId = 1,
        createdAt = "2023-10-01T00:00:00Z",
        updatedAt = "2023-10-01T00:00:00Z"
    ),
    Task(
        id = 5,
        breadcrumb = "Workspace 2 / Project 2",
        title = "Task 5",
        description = "Description of Task 5",
        deadline = "2023-10-01",
        status = "In Progress",
        projectId = 2,
        createdAt = "2023-10-01T00:00:00Z",
        updatedAt = "2023-10-01T00:00:00Z"
    )
)