package com.ucapdm2025.taskspaces.data.remote.services

import com.ucapdm2025.taskspaces.data.remote.requests.TagRequest
import com.ucapdm2025.taskspaces.data.remote.responses.BaseResponse
import com.ucapdm2025.taskspaces.data.remote.responses.TagResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TagService {
    @GET("tags/p/{projectId}")
    suspend fun getTagsByProjectId(@Path("projectId") projectId: Int): BaseResponse<List<TagResponse>>

    @GET("tags/t/{taskId}")
    suspend fun getTagsByTaskId(@Path("taskId") taskId: Int): BaseResponse<List<TagResponse>>

    @GET("tags/{id}")
    suspend fun getTagById(@Path("id") id: Int): BaseResponse<TagResponse>

    @POST("tags/p/{projectId}")
    suspend fun createTag(@Path("projectId") projectId: Int, @Body request: TagRequest): BaseResponse<TagResponse>

    @PUT("tags/{id}")
    suspend fun updateTag(@Path("id") id: Int, @Body request: TagRequest): BaseResponse<TagResponse>

    @DELETE("tags/{id}")
    suspend fun deleteTag(@Path("id") id: Int): BaseResponse<TagResponse>

    @POST("tags/{id}/t/{taskId}")
    suspend fun assignTagToTask(@Path("id") id: Int, @Path("taskId") taskId: Int): BaseResponse<TagResponse>

    @DELETE("tags/{id}/t/{taskId}")
    suspend fun unassignTagFromTask(@Path("id") id: Int, @Path("taskId") taskId: Int): BaseResponse<TagResponse>
}