package com.ucapdm2025.taskspaces.data.remote.services.workspace

import com.ucapdm2025.taskspaces.data.database.entities.WorkspaceEntity
import com.ucapdm2025.taskspaces.data.remote.requests.WorkspaceRequest
import com.ucapdm2025.taskspaces.data.remote.responses.BaseResponse
import com.ucapdm2025.taskspaces.data.remote.responses.WorkspaceResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface WorkspaceService {
    @GET("workspaces/{id}")
    suspend fun getWorkspaceById(@Path("id") id: Int): BaseResponse<WorkspaceResponse>

    @GET("workspaces/u/{userId}")
    suspend fun getWorkspacesByUserId(@Path("userId") userId: Int): BaseResponse<List<WorkspaceResponse>>

    @POST("workspaces/")
    suspend fun createWorkspace(@Body request: WorkspaceRequest): BaseResponse<WorkspaceResponse>

    @PUT("workspace/{id}")
    suspend fun updateWorkspace(@Path("id") id: Int, workspace: WorkspaceEntity): BaseResponse<WorkspaceResponse>

    @DELETE("workspace/{id}")
    suspend fun deleteWorkspace(@Path("id") id: Int): BaseResponse<Unit>
}

