package com.ucapdm2025.taskspaces.data.dummy

import com.ucapdm2025.taskspaces.data.model.Tag
import com.ucapdm2025.taskspaces.data.model.Task
import com.ucapdm2025.taskspaces.data.model.User

val tasksDummy: List<Task> = listOf(
    Task(
        id = 1,
        title = "Task 1",
        description = "Description of Task 1",
        date = "2023-10-01",
        timer = 120,
        status = "In Progress",
        tags = tagsDummy,
        assignedMembers = usersDummy,
        createdAt = "2023-10-01T00:00:00Z",
        updatedAt = "2023-10-01T00:00:00Z"
    ),
    Task(
        id = 2,
        title = "Task 2",
        description = "Description of Task 2",
        date = "2023-10-02",
        timer = 60,
        status = "Completed",
        tags = listOf<Tag>(
            tagsDummy.first()
        ),
        assignedMembers = listOf<User>(
            usersDummy.first()
        ),
        createdAt = "2023-10-02T00:00:00Z",
        updatedAt = "2023-10-02T00:00:00Z"
    )
)

val bookmarkedTasksDummy: List<Task> = listOf(
    Task(
        id = 3,
        title = "Task 3",
        description = "Description of Task 3",
        date = "2023-10-01",
        timer = 120,
        status = "In Progress",
        tags = tagsDummy,
        assignedMembers = usersDummy,
        createdAt = "2023-10-01T00:00:00Z",
        updatedAt = "2023-10-01T00:00:00Z"
    ),
    Task(
        id = 4,
        title = "Task 4",
        description = "Description of Task 4",
        date = "2023-10-01",
        timer = 120,
        status = "In Progress",
        tags = tagsDummy,
        assignedMembers = usersDummy,
        createdAt = "2023-10-01T00:00:00Z",
        updatedAt = "2023-10-01T00:00:00Z"
    ),
    Task(
        id = 5,
        title = "Task 5",
        description = "Description of Task 5",
        date = "2023-10-01",
        timer = 120,
        status = "In Progress",
        tags = tagsDummy,
        assignedMembers = usersDummy,
        createdAt = "2023-10-01T00:00:00Z",
        updatedAt = "2023-10-01T00:00:00Z"
    )
)