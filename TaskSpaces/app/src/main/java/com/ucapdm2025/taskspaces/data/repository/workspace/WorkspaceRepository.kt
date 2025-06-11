package com.ucapdm2025.taskspaces.data.repository.workspace

import com.ucapdm2025.taskspaces.data.model.UserModel
import com.ucapdm2025.taskspaces.data.model.WorkspaceModel
import kotlinx.coroutines.flow.Flow

interface WorkspaceRepository {
    fun getWorkspacesByUserId(ownerId: Int): Flow<List<WorkspaceModel>>
    fun getWorkspacesSharedWithMe(ownerId: Int): Flow<List<WorkspaceModel>>
    fun getWorkspaceById(id: Int): Flow<WorkspaceModel?>
    suspend fun createWorkspace(title: String, ownerId: Int): WorkspaceModel
    suspend fun updateWorkspace(id: Int, title: String, ownerId: Int): WorkspaceModel
    suspend fun deleteWorkspace(id: Int): Boolean
    fun getMembersByWorkspaceId(workspaceId: Int): Flow<List<UserModel>>
    suspend fun addMember(username: String, memberRole: String, workspaceId: Int): Boolean
    suspend fun removeMember(username: String, workspaceId: Int): Boolean
}