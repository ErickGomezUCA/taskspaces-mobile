package com.ucapdm2025.taskspaces.data.remote.requests.workspace.members

/**
 * UpdateMemberRoleRequest is a data class that represents a request to update the role of a member in a workspace.
 *
 * @property memberRole The new role to be assigned to the member in the workspace.
 */
data class UpdateMemberRoleRequest (
    val memberRole: String
)