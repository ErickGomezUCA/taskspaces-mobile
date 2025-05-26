package com.ucapdm2025.taskspaces.data.repository.workspace

import android.util.Log
import com.ucapdm2025.taskspaces.data.dummy.tasksDummy
import com.ucapdm2025.taskspaces.data.dummy.workspacesDummy
import com.ucapdm2025.taskspaces.data.dummy.workspacesSharedDummy
import com.ucapdm2025.taskspaces.data.model.Task
import com.ucapdm2025.taskspaces.data.model.Workspace
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

// TODO: Implement RoomDatabase to this repository
class WorkspaceRepositoryImpl: WorkspaceRepository {
    private val workspaces = MutableStateFlow(workspacesDummy)
    private val workspacesSharedWithMe = MutableStateFlow(workspacesSharedDummy)

    override fun getWorkspaces(): Flow<List<Workspace>> {
        return workspaces.asStateFlow()
    }

    override fun getWorkspacesSharedWithMe(): Flow<List<Workspace>> {
        return workspacesSharedWithMe.asStateFlow()
    }

    override suspend fun getWorkspaceById(id: Int): Workspace? {
//        delay(1000) // Simulate network delay
        return workspaces.value.find { it.id == id }
    }

    override suspend fun createWorkspace(workspace: Workspace): Workspace {
//        delay(1000) // Simulate network delay
        workspaces.value = workspaces.value + workspace
        return workspace
    }

    override suspend fun updateWorkspace(workspace: Workspace): Workspace {
//        delay(1000) // Simulate network delay

        workspaces.value = workspaces.value.map {
            if (it.id == workspace.id) workspace else it
        }

        return workspace
    }

    override suspend fun deleteWorkspace(id: Int): Boolean {
//        delay(1000) // Simulate network delay

        val exists = workspaces.value.any { it.id == id}

        if (exists) {
            workspaces.value = workspaces.value.filter { it.id != id }
        }

        return exists
    }
}