package com.ucapdm2025.taskspaces.data.remote.services

import com.ucapdm2025.taskspaces.data.remote.requests.workspace.WorkspaceRequest
import com.ucapdm2025.taskspaces.data.remote.requests.workspace.members.InviteWorkspaceMemberRequest
import com.ucapdm2025.taskspaces.data.remote.responses.BaseResponse
import com.ucapdm2025.taskspaces.data.remote.responses.workspace.WorkspaceMemberResponse
import com.ucapdm2025.taskspaces.data.remote.responses.workspace.WorkspaceResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * WorkspaceService is an interface that defines the workspace-related API endpoints.
 * It includes methods for creating, retrieving, updating, and deleting workspaces.
 */
interface WorkspaceService {
    @GET("workspaces/{id}")
    suspend fun getWorkspaceById(@Path("id") id: Int): BaseResponse<WorkspaceResponse>

    @GET("workspaces/u/{userId}")
    suspend fun getWorkspacesByUserId(@Path("userId") userId: Int): BaseResponse<List<WorkspaceResponse>>

    @POST("workspaces/")
    suspend fun createWorkspace(@Body request: WorkspaceRequest): BaseResponse<WorkspaceResponse>

    @PUT("workspaces/{id}")
    suspend fun updateWorkspace(@Path("id") id: Int, @Body request: WorkspaceRequest): BaseResponse<WorkspaceResponse>

    @DELETE("workspaces/{id}")
    suspend fun deleteWorkspace(@Path("id") id: Int): BaseResponse<WorkspaceResponse>

//    Members
    @GET("workspaces/{workspaceId}/members")
    suspend fun getMembersByWorkspaceId(@Path("workspaceId") workspaceId: Int): BaseResponse<List<WorkspaceMemberResponse>>

    @POST("workspaces/{workspaceId}/members")
    suspend fun inviteMember(@Path("workspaceId") workspaceId: Int, @Body request: InviteWorkspaceMemberRequest): BaseResponse<WorkspaceMemberResponse>
}