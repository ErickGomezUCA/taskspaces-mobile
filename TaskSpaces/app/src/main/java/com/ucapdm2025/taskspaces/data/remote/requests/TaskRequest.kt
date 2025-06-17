package com.ucapdm2025.taskspaces.data.remote.requests

import com.ucapdm2025.taskspaces.ui.components.projects.StatusVariations

/**
 * TaskRequest is a data class that represents the request body for creating or updating a task.
 *
 * @property title The title of the task.
 * @property description An optional description of the task.
 * @property status The status of the task, represented by StatusVariations.
 * @property deadline An optional deadline for the task, represented as a String (TODO: should be LocalDateTime).
 * @property timer An optional timer for the task, represented as a Float.
 */
data class TaskRequest (
    val title: String,
    val description: String? = null,
    val deadline: String? = null, // TODO: UseLocalDateTime instead of String
    val timer: Float? = null,
    val status: StatusVariations = StatusVariations.PENDING,
)