package com.ucapdm2025.taskspaces.ui.components.workspace

/**
 * Enum class representing the different roles a member can have in a workspace.
 */
enum class MemberRoles(val id: Int, val value: String, val label: String) {
    READER(1, "READER", "Reader"),
    COLLABORATOR(2, "COLLABORATOR", "Collaborator"),
    ADMINISTRATOR(3, "ADMIN", "Administrator"),
}