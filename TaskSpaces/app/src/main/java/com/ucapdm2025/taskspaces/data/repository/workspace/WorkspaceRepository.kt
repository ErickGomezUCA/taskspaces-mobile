package com.ucapdm2025.taskspaces.data.repository.workspace

import com.ucapdm2025.taskspaces.data.model.Task
import com.ucapdm2025.taskspaces.data.model.Workspace
import kotlinx.coroutines.flow.Flow

interface WorkspaceRepository {
    fun getWorkspaces(): Flow<List<Workspace>>
    fun getWorkspacesSharedWithMe(): Flow<List<Workspace>>
    fun getAssignedTasks(): Flow<List<Task>>
    suspend fun getWorkspaceById(id: Int): Workspace?
    suspend fun createWorkspace(workspace: Workspace): Workspace
    suspend fun updateWorkspace(workspace: Workspace): Workspace
    suspend fun deleteWorkspace(id: Int): Boolean
}