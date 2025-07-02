package com.ucapdm2025.taskspaces.data.model.relational

import com.ucapdm2025.taskspaces.data.database.entities.relational.WorkspaceMemberEntity
import com.ucapdm2025.taskspaces.data.model.UserModel
import com.ucapdm2025.taskspaces.ui.components.workspace.MemberRoles

/**
 * WorkspaceMemberModel represents a member of a workspace in the application.
 *
 * @property workspaceId The ID of the workspace to which the member belongs.
 * @property user The UserModel representing the user who is a member of the workspace.
 * @property memberRole The role assigned to the member within the workspace, represented as MemberRoles.
 */
data class WorkspaceMemberModel(
    val workspaceId: Int,
    val user: UserModel,
    val memberRole: MemberRoles
)

/**
 * Converts WorkspaceMemberEntity to WorkspaceMemberModel for domain representation.
 *
 * @return WorkspaceMemberModel representing the workspace member in the domain layer.
 */
fun WorkspaceMemberModel.toDatabase(): WorkspaceMemberEntity {
    return WorkspaceMemberEntity(
        workspaceId = workspaceId,
        userId = user.id,
        memberRoleId = memberRole.id
    )
}