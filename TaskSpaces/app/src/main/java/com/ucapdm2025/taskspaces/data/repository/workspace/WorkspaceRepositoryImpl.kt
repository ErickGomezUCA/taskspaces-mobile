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
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime

// TODO: Implement RoomDatabase to this repository
class WorkspaceRepositoryImpl: WorkspaceRepository {
    private val workspaces = MutableStateFlow(workspacesDummy)
    private val workspacesSharedWithMe = MutableStateFlow(workspacesSharedDummy)

    private var autoIncrementId = workspaces.value.size + 1

    override fun getWorkspacesByUserId(ownerId: Int): Flow<List<Workspace>> {
        return workspaces
            .map { list -> list.filter { it.ownerId == ownerId } }
    }

    // TODO: Refactor workspacesShared with a relational approach
    override fun getWorkspacesSharedWithMe(ownerId: Int): Flow<List<Workspace>> {
        return workspacesSharedWithMe.asStateFlow()
    }

    override fun getWorkspaceById(id: Int): Flow<Workspace?> {
        return workspaces.value.find { it.id == id }
            ?.let { MutableStateFlow(it) }
            ?: MutableStateFlow(null)
    }

    override suspend fun createWorkspace(title: String, ownerId: Int): Workspace {
        val createdWorkspace = Workspace(
            id = autoIncrementId++,
            title = title,
            ownerId = ownerId,
            createdAt = LocalDateTime.now().toString(),
            updatedAt = LocalDateTime.now().toString()
        )

        workspaces.value = workspaces.value + createdWorkspace

        return createdWorkspace
    }

    override suspend fun updateWorkspace(id: Int, title: String, ownerId: Int): Workspace {
        val updatedWorkspace = Workspace(
            id = id,
            title = title,
            ownerId = ownerId,
            createdAt = LocalDateTime.now().toString(),
            updatedAt = LocalDateTime.now().toString()
        )

        workspaces.value = workspaces.value.map {
            if (it.id == updatedWorkspace.id) updatedWorkspace else it
        }

        return updatedWorkspace
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