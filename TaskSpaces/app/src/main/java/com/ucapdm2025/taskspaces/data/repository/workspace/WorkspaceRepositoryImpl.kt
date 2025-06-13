package com.ucapdm2025.taskspaces.data.repository.workspace

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.ucapdm2025.taskspaces.data.database.dao.WorkspaceDao
import com.ucapdm2025.taskspaces.data.database.entities.toDomain
import com.ucapdm2025.taskspaces.data.dummy.catalog.workspaceMembersDummy
import com.ucapdm2025.taskspaces.data.dummy.workspacesDummies
import com.ucapdm2025.taskspaces.data.dummy.workspacesSharedDummies
import com.ucapdm2025.taskspaces.data.model.UserModel
import com.ucapdm2025.taskspaces.data.model.WorkspaceModel
import com.ucapdm2025.taskspaces.data.model.toDatabase
import com.ucapdm2025.taskspaces.data.remote.responses.WorkspaceResponse
import com.ucapdm2025.taskspaces.data.remote.responses.toEntity
import com.ucapdm2025.taskspaces.data.remote.services.workspace.WorkspaceService
import com.ucapdm2025.taskspaces.helpers.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime

class WorkspaceRepositoryImpl(
    private val workspaceDao: WorkspaceDao,
    private val workspaceService: WorkspaceService
): WorkspaceRepository {
    private val workspaces = MutableStateFlow(workspacesDummies)
    private val workspacesSharedWithMe = MutableStateFlow(workspacesSharedDummies)
    private val members = MutableStateFlow(workspaceMembersDummy)

    private var autoIncrementId = 0

    override fun getWorkspacesByUserId(ownerId: Int): Flow<Resource<List<WorkspaceModel>>> = flow {
        emit(Resource.Loading)

        try {
//            Fetch workspaces from remote
            val remoteWorkspaces: List<WorkspaceResponse> = workspaceService.getWorkspacesByUserId(ownerId = ownerId).content

//            Save remote workspaces to the database
            if (remoteWorkspaces.isNotEmpty()) {
                remoteWorkspaces.forEach {
                    workspaceDao.createWorkspace(it.toEntity())
                }
            }
        }
        catch(e: Exception) {
            Log.d("WorkspaceRepository: getWorkspacesByUserId", "Error fetching workspaces: ${e.message}")
        }

//        Use local workspaces
        val localWorkspaces = workspaceDao.getWorkspacesByUserId(ownerId = ownerId).map { entities ->
            val workspaces = entities.map { it.toDomain() }

            if (workspaces.isEmpty()) {
//                Logs an error if no workspaces are found for the user
                Resource.Error("No workspace found for user with ID: $ownerId")
            } else {
//                Returns the workspaces as a success (to domain)
                Resource.Success(workspaces)
            }
        }.distinctUntilChanged()

        emitAll(localWorkspaces)
    }.flowOn(Dispatchers.IO)

    // TODO: Refactor workspacesShared with a relational approach
    override fun getWorkspacesSharedWithMe(ownerId: Int): Flow<List<WorkspaceModel>> {
        return workspacesSharedWithMe.asStateFlow()
    }

    override fun getWorkspaceById(id: Int): Flow<Resource<WorkspaceModel?>> = flow {
        emit(Resource.Loading)

        try {
            val remoteWorkspace: WorkspaceResponse? = workspaceService.getWorkspaceById(id = id).content

            if (remoteWorkspace != null) {
                workspaceDao.createWorkspace(remoteWorkspace.toEntity())
            } else {
                Log.d("WorkspaceRepository", "No workspace found with ID: $id")
            }
        } catch (e: Exception) {
            Log.d("WorkspaceRepository: getWorkspaceById", "Error fetching workspaces: ${e.message}")
        }

        val remoteWorkspace = workspaceDao.getWorkspaceById(id = id).map { entity ->
            val workspace = entity?.toDomain()

            if (workspace == null) {
//                Logs an error if no workspaces are found for the user
                Resource.Error("No workspace found")
            } else {
//                Returns the workspaces as a success (to domain)
                Resource.Success(workspace)
            }
        }.distinctUntilChanged()
    }

    override suspend fun createWorkspace(title: String, ownerId: Int) {
        val createdWorkspace = WorkspaceModel(
            id = autoIncrementId++,
            title = title,
            ownerId = ownerId,
            createdAt = LocalDateTime.now().toString(),
            updatedAt = LocalDateTime.now().toString()
        )

        workspaceDao.createWorkspace(workspace = createdWorkspace.toDatabase())
    }

    override suspend fun updateWorkspace(id: Int, title: String, ownerId: Int) {
        val updatedWorkspaceModel = WorkspaceModel(
            id = id,
            title = title,
            ownerId = ownerId,
            createdAt = LocalDateTime.now().toString(),
            updatedAt = LocalDateTime.now().toString()
        )

        workspaceDao.updateWorkspace(workspace = updatedWorkspaceModel.toDatabase())
    }

    override suspend fun deleteWorkspace(id: Int) {
        workspaceDao.deleteWorkspace(id = id)
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