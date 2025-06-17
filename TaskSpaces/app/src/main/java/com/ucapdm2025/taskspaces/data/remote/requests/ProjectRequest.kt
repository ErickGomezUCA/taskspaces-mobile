package com.ucapdm2025.taskspaces.data.remote.requests

/**
 * ProjectRequest is a data class that represents the request body
 * for creating or updating a project in the TaskSpaces application.
 *
 * @property title The title of the project.
 * @property icon An optional icon for the project, represented as a string.
 */
data class ProjectRequest (
    val title: String,
    val icon: String? = null,
)