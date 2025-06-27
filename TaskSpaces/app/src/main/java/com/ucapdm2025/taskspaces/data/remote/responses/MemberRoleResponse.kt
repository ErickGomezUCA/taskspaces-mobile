package com.ucapdm2025.taskspaces.data.remote.responses

import com.ucapdm2025.taskspaces.data.model.MemberRoleModel

/**
 * MemberRoleResponse is a data class that represents the response from the API for a member's role in a workspace.
 *
 * @property role The role of the member in the workspace (e.g., "ADMIN", "COLLABORATOR", "READER").
 */
data class MemberRoleResponse(
    val role: String
)

/**
 * Converts MemberRoleResponse to MemberRoleModel for application use.
 *
 * @return MemberRoleModel representing the member's role in the workspace.
 */
fun MemberRoleResponse.toDomain(): MemberRoleModel {
    return MemberRoleModel(
        role = role
    )
}