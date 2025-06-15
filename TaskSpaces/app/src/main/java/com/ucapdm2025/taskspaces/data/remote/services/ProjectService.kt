package com.ucapdm2025.taskspaces.data.remote.services

import com.ucapdm2025.taskspaces.data.remote.requests.ProjectRequest
import com.ucapdm2025.taskspaces.data.remote.responses.BaseResponse
import com.ucapdm2025.taskspaces.data.remote.responses.ProjectResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProjectService {
    @GET("projects/{id}")
    suspend fun getProjectById(@Path("id") id: Int): BaseResponse<ProjectResponse>

    @GET("projects/w/{workspaceId}")
    suspend fun getProjectsByWorkspaceId(@Path("workspaceId") workspaceId: Int): BaseResponse<List<ProjectResponse>>

    @POST("projects/w/{workspaceId}")
    suspend fun createProject(@Path("workspaceId") workspaceId: Int, @Body request: ProjectRequest): BaseResponse<ProjectResponse>

    @PUT("projects/{id}")
    suspend fun updateProject(@Path("id") id: Int, @Body request: ProjectRequest): BaseResponse<ProjectResponse>

    @DELETE("projects/{id}")
    suspend fun deleteProject(@Path("id") id: Int): BaseResponse<ProjectResponse>
}