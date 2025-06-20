package com.ucapdm2025.taskspaces.data.remote.responses.workspace

import com.ucapdm2025.taskspaces.data.remote.responses.UserResponse

/**
 * WorkspaceMemberResponse is a data class that represents a member of a workspace.
 *
 * @property user The user details of the workspace member.
 * @property memberRole The role of the member in the workspace.
 */
data class WorkspaceMemberResponse(
    val user: UserResponse,
    val memberRole: String,
)