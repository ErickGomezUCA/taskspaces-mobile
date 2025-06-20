package com.ucapdm2025.taskspaces.ui.components.workspace

/**
 * Enum class representing the different roles a member can have in a workspace.
 */
enum class MemberRoles(val value: String) {
    ADMINISTRATOR("Administrator"),
    COLLABORATOR("Collaborator"),
    READER("Reader"),
}