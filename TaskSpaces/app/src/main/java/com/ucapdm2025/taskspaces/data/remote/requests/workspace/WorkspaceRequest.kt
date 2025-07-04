package com.ucapdm2025.taskspaces.data.remote.requests.workspace

/**
 * WorkspaceRequest is a data class that represents the request body for creating or updating a workspace.
 *
 * @property title The title of the workspace.
 */
data class WorkspaceRequest (
    val title: String
)