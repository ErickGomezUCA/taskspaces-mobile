package com.ucapdm2025.taskspaces.data.repository.workspace

import com.ucapdm2025.taskspaces.data.dummy.catalog.workspaceMembersDummy
import com.ucapdm2025.taskspaces.data.dummy.workspacesDummies
import com.ucapdm2025.taskspaces.data.dummy.workspacesSharedDummies
import com.ucapdm2025.taskspaces.data.model.UserModel
import com.ucapdm2025.taskspaces.data.model.WorkspaceModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime

// TODO: Implement RoomDatabase to this repository
class WorkspaceRepositoryImpl: WorkspaceRepository {
    private val workspaces = MutableStateFlow(workspacesDummies)
    private val workspacesSharedWithMe = MutableStateFlow(workspacesSharedDummies)
    private val members = MutableStateFlow(workspaceMembersDummy)

    private var autoIncrementId = workspaces.value.size + 1

    override fun getWorkspacesByUserId(ownerId: Int): Flow<List<WorkspaceModel>> {
        return workspaces
            .map { list -> list.filter { it.ownerId == ownerId } }
    }

    // TODO: Refactor workspacesShared with a relational approach
    override fun getWorkspacesSharedWithMe(ownerId: Int): Flow<List<WorkspaceModel>> {
        return workspacesSharedWithMe.asStateFlow()
    }

    override fun getWorkspaceById(id: Int): Flow<WorkspaceModel?> {
        return workspaces.value.find { it.id == id }
            ?.let { MutableStateFlow(it) }
            ?: MutableStateFlow(null)
    }

    override suspend fun createWorkspace(title: String, ownerId: Int): WorkspaceModel {
        val createdWorkspaceModel = WorkspaceModel(
            id = autoIncrementId++,
            title = title,
            ownerId = ownerId,
            createdAt = LocalDateTime.now().toString(),
            updatedAt = LocalDateTime.now().toString()
        )

        workspaces.value = workspaces.value + createdWorkspaceModel

        return createdWorkspaceModel
    }

    override suspend fun updateWorkspace(id: Int, title: String, ownerId: Int): WorkspaceModel {
        val updatedWorkspaceModel = WorkspaceModel(
            id = id,
            title = title,
            ownerId = ownerId,
            createdAt = LocalDateTime.now().toString(),
            updatedAt = LocalDateTime.now().toString()
        )

        workspaces.value = workspaces.value.map {
            if (it.id == updatedWorkspaceModel.id) updatedWorkspaceModel else it
        }

        return updatedWorkspaceModel
    }

    override suspend fun deleteWorkspace(id: Int): Boolean {
//        delay(1000) // Simulate network delay

        val exists = workspaces.value.any { it.id == id}

        if (exists) {
            workspaces.value = workspaces.value.filter { it.id != id }
        }

        return exists
    }

//    Members
    override fun getMembersByWorkspaceId(workspaceId: Int): Flow<List<UserModel>> {
        return members.asStateFlow()
    }

    override suspend fun addMember(username: String, memberRole: String, workspaceId: Int): Boolean {
        val userExists = members.value.find { it.username == username }
        val workspaceExists = workspaces.value.any { it.id == workspaceId }

        if (userExists != null && workspaceExists) {
            members.value = members.value + userExists
            return true
        }

        return false
    }

    override suspend fun removeMember(username: String, workspaceId: Int): Boolean {
        val userExists = members.value.find { it.username == username }
        val workspaceExists = workspaces.value.any { it.id == workspaceId }

        if (userExists != null && workspaceExists) {
            members.value = members.value.filter { it.username != username }
            return true
        }

        return false
    }
}