package com.ucapdm2025.taskspaces.data.remote.services

import com.ucapdm2025.taskspaces.data.remote.responses.BaseResponse
import com.ucapdm2025.taskspaces.data.remote.responses.MediaResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * MediaService is an interface that defines the API endpoints for media-related operations.
 */
interface MediaService {
    @Multipart
    @POST("media/upload")
    suspend fun uploadMedia(@Part media: MultipartBody.Part): BaseResponse<MediaResponse>
}