package com.ucapdm2025.taskspaces.data.dummy.catalog

import com.ucapdm2025.taskspaces.data.model.User

val workspaceMembersDummy: List<User> = listOf(
    User(
        id = 1,
        fullname = "John Doe",
        username = "johndoe",
        email = "johndoe@email.com",
        avatar = "https://example.com/avatar1.png",
        createdAt = "2023-10-01T12:00:00Z",
        updatedAt = "2023-10-01T12:00:00Z"
    ),
    User(
        id = 2,
        fullname = "John Doe 2",
        username = "johndoe2",
        email = "johndoe2@email.com",
        avatar = "https://example.com/avatar2.png",
        createdAt = "2023-10-01T12:00:00Z",
        updatedAt = "2023-10-01T12:00:00Z"
    )
)