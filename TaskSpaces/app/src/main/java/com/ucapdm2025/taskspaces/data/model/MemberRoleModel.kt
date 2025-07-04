package com.ucapdm2025.taskspaces.data.model

/**
 * MemberRoleModel is a data class that represents the role of a member in a workspace.
 *
 * @property role The role of the member in the workspace (e.g., "ADMIN", "COLLABORATOR", "READER").
 */
data class MemberRoleModel (
    val role: String
)