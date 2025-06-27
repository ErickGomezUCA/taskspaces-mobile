package com.ucapdm2025.taskspaces.data.repository.memberRole

import android.util.Log
import com.ucapdm2025.taskspaces.data.model.MemberRoleModel
import com.ucapdm2025.taskspaces.data.remote.responses.MemberRoleResponse
import com.ucapdm2025.taskspaces.data.remote.responses.toDomain
import com.ucapdm2025.taskspaces.data.remote.services.MemberRoleService
import com.ucapdm2025.taskspaces.helpers.Resource
import com.ucapdm2025.taskspaces.ui.components.workspace.MemberRoles
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MemberRoleRepositoryImpl(
    private val memberRoleService: MemberRoleService
) : MemberRoleRepository {
    override suspend fun getMemberRoleByWorkspaceId(workspaceId: Int): Flow<Resource<MemberRoleModel>> =
        flow {
            emit(Resource.Loading)

            try {
//            Fetch role from remote
                val remoteRole: MemberRoleResponse? =
                    memberRoleService.getMemberRoleByWorkspaceId(workspaceId = workspaceId).content

                Log.d("test1", memberRoleService.getMemberRoleByWorkspaceId(workspaceId = workspaceId).toString())

                if (remoteRole != null) {
                    emit(Resource.Success(remoteRole.toDomain()))
                } else {
                    emit(Resource.Error("No member role found for workspace ID: $workspaceId"))
                }
            } catch (e: Exception) {
                Log.d(
                    "MemberRoleRepositoryImpl: getMemberRoleByWorkspaceId",
                    "Error fetching role: ${e.message}"
                )
            }
        }

    override suspend fun getMemberRoleByProjectId(projectId: Int): Flow<Resource<MemberRoleModel>> =
        flow {
            flow {
                emit(Resource.Loading)

                try {
//            Fetch role from remote
                    val remoteRole: MemberRoleResponse? =
                        memberRoleService.getMemberRoleByProjectId(projectId = projectId).content

                    if (remoteRole != null) {
                        emit(Resource.Success(remoteRole.toDomain()))
                    } else {
                        emit(Resource.Error("No member role found for project ID: $projectId"))
                    }
                } catch (e: Exception) {
                    Log.d(
                        "MemberRoleRepositoryImpl: getMemberRoleByProjectId",
                        "Error fetching role: ${e.message}"
                    )
                }
            }

        }

    override suspend fun getMemberRoleByTaskId(taskId: Int): Flow<Resource<MemberRoleModel>> =
        flow {
            emit(Resource.Loading)

            try {
//            Fetch role from remote
                val remoteRole: MemberRoleResponse? =
                    memberRoleService.getMemberRoleByTaskId(taskId = taskId).content

                if (remoteRole != null) {
                    emit(Resource.Success(remoteRole.toDomain()))
                } else {
                    emit(Resource.Error("No member role found for task ID: $taskId"))
                }
            } catch (e: Exception) {
                Log.d(
                    "MemberRoleRepositoryImpl: getMemberRoleByTaskId",
                    "Error fetching role: ${e.message}"
                )
            }
        }

    override suspend fun hasSufficientPermissions(
        workspaceId: Int?,
        projectId: Int?,
        taskId: Int?,
        minimumRole: MemberRoles
    ): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading)
        try {
//            Fetch role from remote
            val remoteRole: MemberRoleResponse? =
                if (workspaceId != null) {
                    memberRoleService.getMemberRoleByWorkspaceId(workspaceId = workspaceId).content
                } else if (projectId != null) {
                    memberRoleService.getMemberRoleByProjectId(projectId = projectId).content
                } else if (taskId != null) {
                    memberRoleService.getMemberRoleByTaskId(taskId = taskId).content
                } else {
                    emit(Resource.Error("No ID provided for role check"))
                    return@flow
                }

            if (remoteRole != null) {
                val parsedRole = MemberRoles.valueOf(remoteRole.role)


                if (parsedRole >= minimumRole) {
                    emit(Resource.Success(true))
                } else {
                    emit(Resource.Success(false))
                }
            } else {
                emit(Resource.Error("No member role found for task ID: $taskId"))
            }
        } catch (e: Exception) {
            Log.d(
                "MemberRoleRepositoryImpl: getMemberRoleByTaskId",
                "Error fetching role: ${e.message}"
            )
        }
    }
}