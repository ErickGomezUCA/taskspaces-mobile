package com.ucapdm2025.taskspaces.data.remote.services

import com.ucapdm2025.taskspaces.data.remote.requests.CommentRequest
import com.ucapdm2025.taskspaces.data.remote.responses.BaseResponse
import com.ucapdm2025.taskspaces.data.remote.responses.CommentResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * CommentService is an interface that defines the API endpoints for managing comments.
 */
interface CommentService {
    @GET("comments/t/{taskId}")
    suspend fun getCommentsByTaskId(@Path("taskId") taskId: Int): BaseResponse<List<CommentResponse>>

    @POST("comments/t/{taskId}")
    suspend fun createComment(@Path("taskId") taskId: Int, @Body request: CommentRequest): BaseResponse<CommentResponse>

    @PUT("comments/{commentId}/t/{taskId}")
    suspend fun updateComment(
        @Path("commentId") commentId: Int,
        @Path("taskId") taskId: Int,
        @Body request: CommentRequest
    ): BaseResponse<CommentResponse>

    @DELETE("comments/{commentId}")
    suspend fun deleteComment(@Path("commentId") commentId: Int): BaseResponse<CommentResponse>
}