package com.ucapdm2025.taskspaces.data.remote.workspace

import com.ucapdm2025.taskspaces.data.remote.responses.BaseResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface WorkspaceService {
    @GET("/workspaces/:id")
    suspend fun getWorkspaceById(id: Int): BaseResponse<WorkspaceService>

    @GET("/workspaces/u/:ownerId")
    suspend fun getWorkspacesByUserId(ownerId: Int): BaseResponse<List<WorkspaceService>>

    @POST("/workspaces/")
    suspend fun createWorkspace(workspace: WorkspaceService): BaseResponse<WorkspaceService>

    @PUT("/workspace/:id")
    suspend fun updateWorkspace(id: Int, workspace: WorkspaceService): BaseResponse<WorkspaceService>

    @DELETE("/workspace/:id")
    suspend fun deleteWorkspace(id: Int): BaseResponse<Unit>
}

