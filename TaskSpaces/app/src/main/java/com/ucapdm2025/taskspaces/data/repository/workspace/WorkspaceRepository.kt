package com.ucapdm2025.taskspaces.data.repository.workspace

import com.ucapdm2025.taskspaces.data.model.Task
import com.ucapdm2025.taskspaces.data.model.User
import com.ucapdm2025.taskspaces.data.model.Workspace
import kotlinx.coroutines.flow.Flow

interface WorkspaceRepository {
    fun getWorkspacesByUserId(ownerId: Int): Flow<List<Workspace>>
    fun getWorkspacesSharedWithMe(ownerId: Int): Flow<List<Workspace>>
    fun getWorkspaceById(id: Int): Flow<Workspace?>
    suspend fun createWorkspace(title: String, ownerId: Int): Workspace
    suspend fun updateWorkspace(id: Int, title: String, ownerId: Int): Workspace
    suspend fun deleteWorkspace(id: Int): Boolean
    fun getMembersByWorkspaceId(workspaceId: Int): Flow<List<User>>
    suspend fun addMember(username: String, memberRole: String, workspaceId: Int): Boolean
    suspend fun removeMember(username: String, workspaceId: Int): Boolean
}