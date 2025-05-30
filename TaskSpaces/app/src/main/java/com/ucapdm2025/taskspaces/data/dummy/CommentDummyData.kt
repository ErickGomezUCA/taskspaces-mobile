package com.ucapdm2025.taskspaces.data.dummy

import com.ucapdm2025.taskspaces.data.model.Comment

val commentsDummy: List<Comment> = listOf(
    Comment(
        id = 1,
        content = "This is the first comment.",
        authorId = 1,
        taskId = 1,
        createdAt = "2023-10-01T12:00:00Z",
        updatedAt = "2023-10-01T12:00:00Z"
    ),
    Comment(
        id = 2,
        content = "This is the second comment.",
        authorId = 2,
        taskId = 1,
        createdAt = "2023-10-02T12:00:00Z",
        updatedAt = "2023-10-02T12:00:00Z"
    ),
    Comment(
        id = 3,
        content = "This is the third comment.",
        authorId = 3,
        taskId = 2,
        createdAt = "2023-10-03T12:00:00Z",
        updatedAt = "2023-10-03T12:00:00Z"
    )
)