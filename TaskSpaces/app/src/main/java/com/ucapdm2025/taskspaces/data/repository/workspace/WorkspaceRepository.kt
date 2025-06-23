package com.ucapdm2025.taskspaces.data.repository.workspace

import com.ucapdm2025.taskspaces.data.model.UserModel
import com.ucapdm2025.taskspaces.data.model.WorkspaceModel
import com.ucapdm2025.taskspaces.data.model.relational.WorkspaceMemberModel
import com.ucapdm2025.taskspaces.helpers.Resource
import com.ucapdm2025.taskspaces.ui.components.workspace.MemberRoles
import kotlinx.coroutines.flow.Flow

/**
 * WorkspaceRepository is an interface that defines the methods for managing workspaces in the application.
 * It provides methods to get workspaces by user ID, get workspaces shared with a user, get a workspace by its ID,
 * create a new workspace, update an existing workspace, delete a workspace, get members of a workspace,
 * add a member to a workspace, and remove a member from a workspace.
 */
interface WorkspaceRepository {
    fun getWorkspacesByUserId(ownerId: Int): Flow<Resource<List<WorkspaceModel>>>
    fun getWorkspacesSharedWithMe(ownerId: Int): Flow<List<WorkspaceModel>>
    fun getWorkspaceById(id: Int): Flow<Resource<WorkspaceModel?>>
    suspend fun createWorkspace(title: String): Result<WorkspaceModel>
    suspend fun updateWorkspace(id: Int, title: String): Result<WorkspaceModel>
    suspend fun deleteWorkspace(id: Int): Result<WorkspaceModel>

    fun getMembersByWorkspaceId(workspaceId: Int): Flow<Resource<List<WorkspaceMemberModel>>>
    suspend fun inviteMember(username: String, memberRole: MemberRoles, workspaceId: Int): Result<WorkspaceMemberModel>
    suspend fun updateMember(userId: Int, memberRole: MemberRoles, workspaceId: Int): Result<WorkspaceMemberModel>
}