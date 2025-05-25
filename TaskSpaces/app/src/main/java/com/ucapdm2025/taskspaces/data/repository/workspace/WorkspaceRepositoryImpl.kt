package com.ucapdm2025.taskspaces.data.repository.workspace

import com.ucapdm2025.taskspaces.data.dummy.workspacesDummy
import com.ucapdm2025.taskspaces.data.model.Workspace
import kotlinx.coroutines.delay

// TODO: Implement RoomDatabase to this repository
class WorkspaceRepositoryImpl: WorkspaceRepository {
    private var workspaces: MutableList<Workspace> = workspacesDummy.toMutableList()

    override suspend fun getWorkspaces(): List<Workspace> {
        delay(1000) // Simulate network delay
        return workspaces
    }

    override suspend fun getWorkspaceById(id: Int): Workspace? {
        delay(1000) // Simulate network delay
        return workspaces.find { it.id == id }
    }

    override suspend fun createWorkspace(workspace: Workspace): Workspace {
        delay(1000) // Simulate network delay
        workspaces.add(workspace)
        return workspace
    }

    override suspend fun updateWorkspace(workspace: Workspace): Workspace {
        delay(1000) // Simulate network delay
        val index = workspaces.indexOfFirst { it.id == workspace.id }
        if (index != -1) {
            workspaces[index] = workspace
        }
        return workspace
    }

    override suspend fun deleteWorkspace(id: Int): Boolean {
        delay(1000) // Simulate network delay
        val index = workspaces.indexOfFirst { it.id == id }
        return if (index != -1) {
            workspaces.removeAt(index)
            true
        } else {
            false
        }
    }
}