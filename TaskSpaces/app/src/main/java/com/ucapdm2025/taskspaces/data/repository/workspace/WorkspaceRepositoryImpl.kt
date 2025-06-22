package com.ucapdm2025.taskspaces.data.repository.workspace

import android.util.Log
import coil3.network.HttpException
import com.ucapdm2025.taskspaces.data.database.dao.WorkspaceDao
import com.ucapdm2025.taskspaces.data.database.dao.relational.WorkspaceMemberDao
import com.ucapdm2025.taskspaces.data.database.entities.relational.toDomain
import com.ucapdm2025.taskspaces.data.database.entities.toDomain
import com.ucapdm2025.taskspaces.data.dummy.catalog.workspaceMembersDummy
import com.ucapdm2025.taskspaces.data.dummy.workspacesDummies
import com.ucapdm2025.taskspaces.data.dummy.workspacesSharedDummies
import com.ucapdm2025.taskspaces.data.model.UserModel
import com.ucapdm2025.taskspaces.data.model.WorkspaceModel
import com.ucapdm2025.taskspaces.data.model.relational.WorkspaceMemberModel
import com.ucapdm2025.taskspaces.data.model.relational.toDatabase
import com.ucapdm2025.taskspaces.data.model.toDatabase
import com.ucapdm2025.taskspaces.data.remote.requests.workspace.WorkspaceRequest
import com.ucapdm2025.taskspaces.data.remote.requests.workspace.members.InviteWorkspaceMemberRequest
import com.ucapdm2025.taskspaces.data.remote.responses.toDomain
import com.ucapdm2025.taskspaces.data.remote.responses.workspace.WorkspaceMemberResponse
import com.ucapdm2025.taskspaces.data.remote.responses.workspace.WorkspaceResponse
import com.ucapdm2025.taskspaces.data.remote.responses.workspace.toDomain
import com.ucapdm2025.taskspaces.data.remote.responses.workspace.toEntity
import com.ucapdm2025.taskspaces.data.remote.services.WorkspaceService
import com.ucapdm2025.taskspaces.helpers.Resource
import com.ucapdm2025.taskspaces.ui.components.workspace.MemberRoles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.io.IOException
import kotlin.collections.forEach
import kotlin.collections.map
import kotlin.collections.mapNotNull

/**
 * WorkspaceRepositoryImpl is an implementation of the WorkspaceRepository interface.
 * It provides methods to manage workspaces in the application, including retrieving,
 * creating, updating, and deleting workspaces, as well as managing workspace members.
 */
class WorkspaceRepositoryImpl(
    private val workspaceDao: WorkspaceDao,
    private val workspaceMemberDao: WorkspaceMemberDao,
    private val workspaceService: WorkspaceService
) : WorkspaceRepository {
    private val workspaces = MutableStateFlow(workspacesDummies)
    private val workspacesSharedWithMe = MutableStateFlow(workspacesSharedDummies)
    private val members = MutableStateFlow(workspaceMembersDummy)

    override fun getWorkspacesByUserId(ownerId: Int): Flow<Resource<List<WorkspaceModel>>> = flow {
        emit(Resource.Loading)

        try {
//            Fetch workspaces from remote
            val remoteWorkspaces: List<WorkspaceResponse> =
                workspaceService.getWorkspacesByUserId(userId = ownerId).content

//            Save remote workspaces to the database
            if (remoteWorkspaces.isNotEmpty()) {
                remoteWorkspaces.forEach {
                    workspaceDao.createWorkspace(it.toEntity())
                }
            }
        } catch (e: Exception) {
            Log.d(
                "WorkspaceRepository: getWorkspacesByUserId",
                "Error fetching workspaces: ${e.message}"
            )
        }

//        Use local workspaces
        val localWorkspaces =
            workspaceDao.getWorkspacesByUserId(ownerId = ownerId).map { entities ->
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
            val remoteWorkspace: WorkspaceResponse? =
                workspaceService.getWorkspaceById(id = id).content

            if (remoteWorkspace != null) {
                workspaceDao.createWorkspace(remoteWorkspace.toEntity())
            } else {
                Log.d("WorkspaceRepository", "No workspace found with ID: $id")
            }
        } catch (e: Exception) {
            Log.d(
                "WorkspaceRepository: getWorkspaceById",
                "Error fetching workspaces: ${e.message}"
            )
        }

        val localWorkspace = workspaceDao.getWorkspaceById(id = id).map { entity ->
            val workspace = entity?.toDomain()

            if (workspace == null) {
//                Logs an error if no workspaces are found for the user
                Resource.Error("No workspace found")
            } else {
//                Returns the workspaces as a success (to domain)
                Resource.Success(workspace)
            }
        }.distinctUntilChanged()

        emitAll(localWorkspace)
    }

    override suspend fun createWorkspace(title: String): Result<WorkspaceModel> {
        val request = WorkspaceRequest(title)

        return try {
            val response = workspaceService.createWorkspace(request)

            val createdWorkspace: WorkspaceModel = response.content.toDomain()

//            Create retrieved workspace from remote server into the local database
            workspaceDao.createWorkspace(workspace = createdWorkspace.toDatabase())

            Log.d(
                "WorkspaceRepository: createWorkspace",
                "Workspace created successfully: $createdWorkspace"
            )

            Result.success(createdWorkspace)
        } catch (e: HttpException) {
            Log.e("WorkspaceRepository: createWorkspace", "Error creating workspace: ${e.message}")
            Result.failure(e)
        } catch (e: IOException) {
            Log.e("WorkspaceRepository: createWorkspace", "Network error: ${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("WorkspaceRepository: createWorkspace", "Unexpected error: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun updateWorkspace(id: Int, title: String): Result<WorkspaceModel> {
        val request = WorkspaceRequest(title)

        return try {
            val response = workspaceService.updateWorkspace(id, request)

            val updatedWorkspace: WorkspaceModel = response.content.toDomain()

//            Update retrieved workspace from remote server into the local database
            workspaceDao.updateWorkspace(workspace = updatedWorkspace.toDatabase())

            Log.d(
                "WorkspaceRepository: updateWorkspace",
                "Workspace updated successfully: $updatedWorkspace"
            )

            Result.success(updatedWorkspace)
        } catch (e: HttpException) {
            Log.e("WorkspaceRepository: updateWorkspace", "Error updating workspace: ${e.message}")
            Result.failure(e)
        } catch (e: IOException) {
            Log.e("WorkspaceRepository: updateWorkspace", "Network error: ${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("WorkspaceRepository: updateWorkspace", "Unexpected error: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun deleteWorkspace(id: Int): Result<WorkspaceModel> {
        return try {
            val response = workspaceService.deleteWorkspace(id)

            val deletedWorkspace: WorkspaceModel = response.content.toDomain()

            workspaceDao.deleteWorkspace(workspace = deletedWorkspace.toDatabase())

            Log.d(
                "WorkspaceRepository: deleteWorkspace",
                "Workspace deleted successfully: $deletedWorkspace"
            )

            Result.success(deletedWorkspace)
        } catch (e: HttpException) {
            Log.e("WorkspaceRepository: deleteWorkspace", "Error deleting workspace: ${e.message}")
            Result.failure(e)
        } catch (e: IOException) {
            Log.e("WorkspaceRepository: deleteWorkspace", "Network error: ${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("WorkspaceRepository: deleteWorkspace", "Unexpected error: ${e.message}")
            Result.failure(e)
        }
    }

    //    Members
    override fun getMembersByWorkspaceId(workspaceId: Int): Flow<Resource<List<WorkspaceMemberModel>>> = flow {
        emit(Resource.Loading)

        try {
            //            Fetch workspace members from remote
            val remoteWorkspaceMembers: List<WorkspaceMemberResponse> =
                workspaceService.getMembersByWorkspaceId(workspaceId).content

            //            Save remote workspace members to the database
            if (remoteWorkspaceMembers.isNotEmpty()) {
                remoteWorkspaceMembers.forEach {
                    workspaceMemberDao.createMember(it.toEntity(workspaceId))
                }
            }
        } catch (e: Exception) {
            Log.d(
                "WorkspaceMemberRepository: getMembersByWorkspaceId",
                "Error fetching workspace members: ${e.message}"
            )
        }

//        Use local workspace members
        val localWorkspaceMembers =
            workspaceMemberDao.getMembersByWorkspaceId(workspaceId = workspaceId).map { entities ->
                val workspaceMembers = entities.map {
                    val user: UserModel =
                }

                if (workspaceMembers.isEmpty()) {
                    //                Logs an error if no projects are found for the user
                    Resource.Error("No bookmarks found for  user with ID: $userId")
                } else {
//                    Convert bookmarks into tasks
//                    Because bookmarks only have userId and taskId as their columns, but to obtain
//                    the task information, we need to convert them into TaskModel
                    val localTasks = workspaceMembers.mapNotNull { bookmark ->
                        taskDao.getTaskById(bookmark.taskId).first()?.toDomain()
                    }

//                Returns the tasks bookmarked as a success (to domain)
                    Resource.Success(localTasks)
                }
            }.distinctUntilChanged()

    }


    override suspend fun inviteMember(
        username: String,
        memberRole: MemberRoles,
        workspaceId: Int
    ): Result<WorkspaceMemberModel> {
        val request = InviteWorkspaceMemberRequest(username, memberRole.value)

        return try {
            val response = workspaceService.inviteMember(workspaceId = workspaceId, request = request)

            val invitedUser: UserModel = response.content.user.toDomain()
            val memberRole: MemberRoles = MemberRoles.valueOf(response.content.memberRole)

            val invitedMember = WorkspaceMemberModel(
                workspaceId = workspaceId,
                user = invitedUser,
                memberRole = memberRole
            )

            workspaceMemberDao.createMember(invitedMember.toDatabase())

            Log.d(
                "WorkspaceRepository: inviteMember",
                "Member invited successfully: $invitedMember"
            )

            Result.success(invitedMember)
        } catch (e: HttpException) {
            Log.e("WorkspaceRepository: inviteMember", "Error inviting member: ${e.message}")
            Result.failure(e)
        } catch (e: IOException) {
            Log.e("WorkspaceRepository: inviteMember", "Network error: ${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("WorkspaceRepository: inviteMember", "Unexpected error: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun addMember(
        username: String,
        memberRole: String,
        workspaceId: Int
    ): Boolean {
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