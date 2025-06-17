package com.ucapdm2025.taskspaces.data.dummy

import com.ucapdm2025.taskspaces.data.model.TaskModel
import com.ucapdm2025.taskspaces.ui.components.projects.StatusVariations
import java.time.LocalDateTime

val assignedTasksDummies: List<TaskModel> = listOf(
    TaskModel(
        id = 6,
        breadcrumb = "Workspace 1 / Project 1",
        title = "Task 6",
        description = "Description of Task 6",
        deadline = "",
        status = StatusVariations.PENDING,
        projectId = 1,
        createdAt = "2023-10-01T00:00:00Z",
        updatedAt = "2023-10-01T00:00:00Z"
    ),
    TaskModel(
        id = 7,
        breadcrumb = "Workspace 2 / Project 2",
        title = "Task 7",
        description = "Description of Task 7",
        deadline = "",
        status = StatusVariations.PENDING,
        projectId = 2,
        createdAt = "2023-10-02T00:00:00Z",
        updatedAt = "2023-10-02T00:00:00Z"
    )
)