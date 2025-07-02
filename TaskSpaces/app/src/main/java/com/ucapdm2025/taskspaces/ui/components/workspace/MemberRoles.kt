package com.ucapdm2025.taskspaces.ui.components.workspace

/**
 * Enum class representing the different roles a member can have in a workspace.
 */
enum class MemberRoles(val id: Int, val value: String) {
    READER(1, "Reader"),
    COLLABORATOR(2, "Collaborator"),
    ADMIN(3, "Administrator"),
}