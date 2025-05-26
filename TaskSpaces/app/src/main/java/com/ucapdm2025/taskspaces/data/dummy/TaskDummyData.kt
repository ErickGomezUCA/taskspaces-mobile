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