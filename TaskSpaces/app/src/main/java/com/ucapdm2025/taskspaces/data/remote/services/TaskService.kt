package com.ucapdm2025.taskspaces.data.remote.services

import com.ucapdm2025.taskspaces.data.remote.requests.TaskRequest
import com.ucapdm2025.taskspaces.data.remote.responses.BaseResponse
import com.ucapdm2025.taskspaces.data.remote.responses.TaskResponse
import com.ucapdm2025.taskspaces.data.remote.responses.UserResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * TaskService is an interface that defines the API endpoints for managing tasks.
 */
interface TaskService {
    @GET("tasks/{id}")
    suspend fun getTaskById(@Path("id") id: Int): BaseResponse<TaskResponse>

    @GET("tasks/p/{projectId}")
    suspend fun getTasksByProjectId(@Path("projectId") projectId: Int): BaseResponse<List<TaskResponse>>

    @POST("tasks/p/{projectId}")
    suspend fun createTask(@Path("projectId") projectId: Int, @Body request: TaskRequest): BaseResponse<TaskResponse>

    @PUT("tasks/{id}")
    suspend fun updateTask(@Path("id") id: Int, @Body request: TaskRequest): BaseResponse<TaskResponse>

    @DELETE("tasks/{id}")
    suspend fun deleteTask(@Path("id") id: Int): BaseResponse<TaskResponse>

//    Members
//    TODO: Implement this in server backend
    @GET("tasks/assigned/{userId}")
    suspend fun getAssignedTasksByUserId(@Path("userId") userId: Int): BaseResponse<List<TaskResponse>>

    @GET("tasks/{taskId}/members")
    suspend fun getMembersByTaskId(@Path("taskId") taskId: Int): BaseResponse<List<UserResponse>>

    @POST("tasks/{taskId}/members/{memberId}")
    suspend fun assignMemberToTask(
        @Path("taskId") taskId: Int,
        @Path("memberId") memberId: Int
    ): BaseResponse<TaskResponse>

    @DELETE("tasks/{taskId}/members/{memberId}")
    suspend fun removeMemberFromTask(
        @Path("taskId") taskId: Int,
        @Path("memberId") memberId: Int
    ): BaseResponse<TaskResponse>
}