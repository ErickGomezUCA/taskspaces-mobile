package com.ucapdm2025.taskspaces.data.repository.memberRole

import com.ucapdm2025.taskspaces.data.model.MemberRoleModel
import com.ucapdm2025.taskspaces.helpers.Resource
import com.ucapdm2025.taskspaces.ui.components.workspace.MemberRoles
import kotlinx.coroutines.flow.Flow

interface MemberRoleRepository {
    suspend fun getMemberRoleByWorkspaceId(workspaceId: Int): Flow<Resource<MemberRoleModel>>
    suspend fun getMemberRoleByProjectId(projectId: Int): Flow<Resource<MemberRoleModel>>
    suspend fun getMemberRoleByTaskId(taskId: Int): Flow<Resource<MemberRoleModel>>
    suspend fun hasSufficientPermissions(
        workspaceId: Int? = null,
        projectId: Int? = null,
        taskId: Int? = null,
        minimumRole: MemberRoles
    ): Flow<Resource<Boolean>>
}