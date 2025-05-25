package com.ucapdm2025.taskspaces.data.repository.workspace

import com.ucapdm2025.taskspaces.data.model.Workspace

interface WorkspaceRepository {
    suspend fun getWorkspaces(): List<Workspace>
    suspend fun getWorkspaceById(id: Int): Workspace?
    suspend fun createWorkspace(workspace: Workspace): Workspace
    suspend fun updateWorkspace(workspace: Workspace): Workspace
    suspend fun deleteWorkspace(id: Int): Boolean
}