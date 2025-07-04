package com.ucapdm2025.taskspaces.data.remote.services

import com.ucapdm2025.taskspaces.data.remote.responses.BaseResponse
import com.ucapdm2025.taskspaces.data.remote.responses.MemberRoleResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MemberRoleService {
    @GET("members/w/{workspaceId}")
    suspend fun getMemberRoleByWorkspaceId(
        @Path("workspaceId") workspaceId: Int
    ): BaseResponse<MemberRoleResponse>

    @GET("members/p/{projectId}")
    suspend fun getMemberRoleByProjectId(
        @Path("projectId") projectId: Int
    ): BaseResponse<MemberRoleResponse>

    @GET("members/t/{taskId}")
    suspend fun getMemberRoleByTaskId(
        @Path("taskId") taskId: Int
    ): BaseResponse<MemberRoleResponse>
}