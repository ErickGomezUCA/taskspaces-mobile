package com.ucapdm2025.taskspaces.data.remote.services.workspace

import com.ucapdm2025.taskspaces.data.database.entities.WorkspaceEntity
import com.ucapdm2025.taskspaces.data.remote.responses.BaseResponse
import com.ucapdm2025.taskspaces.data.remote.responses.WorkspaceResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface WorkspaceService {
    @GET("/workspaces/:id")
    suspend fun getWorkspaceById(id: Int): BaseResponse<WorkspaceResponse>

    @GET("/workspaces/u/:ownerId")
    suspend fun getWorkspacesByUserId(ownerId: Int): BaseResponse<List<WorkspaceResponse>>

    @POST("/workspaces/")
    suspend fun createWorkspace(workspace: WorkspaceEntity): BaseResponse<WorkspaceResponse>

    @PUT("/workspace/:id")
    suspend fun updateWorkspace(id: Int, workspace: WorkspaceEntity): BaseResponse<WorkspaceResponse>

    @DELETE("/workspace/:id")
    suspend fun deleteWorkspace(id: Int): BaseResponse<Unit>
}

