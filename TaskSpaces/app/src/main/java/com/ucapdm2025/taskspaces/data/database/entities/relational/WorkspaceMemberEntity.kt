package com.ucapdm2025.taskspaces.data.database.entities.relational

import androidx.room.Entity
import com.ucapdm2025.taskspaces.data.model.UserModel
import com.ucapdm2025.taskspaces.data.model.relational.WorkspaceMemberModel
import com.ucapdm2025.taskspaces.ui.components.workspace.MemberRoles

/**
 * WorkspaceMemberEntity is a data class that represents a member of a workspace in the database.
 *
 * @property workspaceId The ID of the workspace to which the member belongs.
 * @property userId The ID of the user who is a member of the workspace.
 * @property memberRoleId The ID of the role assigned to the member within the workspace.
 */
@Entity(tableName = "workspace_member", primaryKeys = ["workspaceId", "userId"])
data class WorkspaceMemberEntity(
    val workspaceId: Int,
    val userId: Int,
    val memberRoleId: Int
)

/**
 * Converts WorkspaceMemberEntity to WorkspaceMemberModel for domain representation.
 *
 * @return WorkspaceMemberModel representing the workspace member in the domain layer.
 */
fun WorkspaceMemberEntity.toDomain(user: UserModel, memberRole: MemberRoles): WorkspaceMemberModel {
    return WorkspaceMemberModel(
        workspaceId = workspaceId,
        user = user,
        memberRole = memberRole
    )
}