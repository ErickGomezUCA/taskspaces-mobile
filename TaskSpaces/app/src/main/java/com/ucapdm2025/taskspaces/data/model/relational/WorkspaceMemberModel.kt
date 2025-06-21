package com.ucapdm2025.taskspaces.data.model.relational

import com.ucapdm2025.taskspaces.data.database.entities.relational.WorkspaceMemberEntity

/**
 * WorkspaceMemberModel represents a member of a workspace in the application.
 *
 * @property workspaceId The ID of the workspace to which the member belongs.
 * @property userId The ID of the user who is a member of the workspace.
 * @property memberRoleId The ID of the role assigned to the member within the workspace.
 */
data class WorkspaceMemberModel(
    val workspaceId: Int,
    val userId: Int,
    val memberRoleId: Int
)

/**
 * Converts WorkspaceMemberEntity to WorkspaceMemberModel for domain representation.
 *
 * @return WorkspaceMemberModel representing the workspace member in the domain layer.
 */
fun WorkspaceMemberModel.toDatabase(): WorkspaceMemberEntity {
    return WorkspaceMemberEntity(
        workspaceId = workspaceId,
        userId = userId,
        memberRoleId = memberRoleId
    )
}