package com.ucapdm2025.taskspaces.ui.components.projects

/**
 * Enum class representing the possible status variations for a task.
 *
 * These statuses are used to categorize tasks within the project board columns.
 */
enum class StatusVariations {
    /** Task has not been started yet. */
    PENDING,

    /** Task is currently in progress. */
    DOING,

    /** Task has been completed. */
    DONE,
}