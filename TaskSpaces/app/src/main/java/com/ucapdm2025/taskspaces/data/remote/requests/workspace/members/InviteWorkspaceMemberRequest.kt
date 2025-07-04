package com.ucapdm2025.taskspaces.data.remote.requests.workspace.members

/**
 * InviteWorkspaceMemberRequest is a data class that represents the request body for inviting a member to a workspace.
 *
 * @property username The username of the member to be invited.
 * @property memberRole The role of the member in the workspace (e.g., "admin", "editor", "viewer").
 */
data class InviteWorkspaceMemberRequest(
    val username: String,
    val memberRole: String,
)