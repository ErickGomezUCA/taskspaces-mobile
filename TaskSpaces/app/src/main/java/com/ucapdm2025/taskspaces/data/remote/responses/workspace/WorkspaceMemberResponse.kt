package com.ucapdm2025.taskspaces.data.remote.responses.workspace

import com.ucapdm2025.taskspaces.data.database.entities.relational.WorkspaceMemberEntity
import com.ucapdm2025.taskspaces.data.model.relational.WorkspaceMemberModel
import com.ucapdm2025.taskspaces.data.remote.responses.UserResponse
import com.ucapdm2025.taskspaces.data.remote.responses.toDomain
import com.ucapdm2025.taskspaces.ui.components.workspace.MemberRoles

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

// TODO: Add documentation here
fun WorkspaceMemberResponse.toDomain(workspaceId: Int): WorkspaceMemberModel {
    return WorkspaceMemberModel(
        workspaceId = workspaceId,
        user = user.toDomain(),
        memberRole = MemberRoles.valueOf(memberRole)
    )
}

fun WorkspaceMemberResponse.toEntity(workspaceId: Int): WorkspaceMemberEntity {
    return WorkspaceMemberEntity(
        workspaceId = workspaceId,
        userId = user.toDomain().id,
        memberRoleId = MemberRoles.valueOf(memberRole).id
    )
}