package com.ucapdm2025.taskspaces.data.remote.workspace

import com.ucapdm2025.taskspaces.data.remote.responses.BaseResponse
import retrofit2.http.GET

interface WorkspaceService {
    @GET("/workspaces/:id")
    suspend fun getWorkspaceById(id: String): BaseResponse<WorkspaceService>
}

