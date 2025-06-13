package com.ucapdm2025.taskspaces.data.remote.services.user

import com.ucapdm2025.taskspaces.data.remote.responses.BaseResponse
import com.ucapdm2025.taskspaces.data.remote.responses.UserResponse
import retrofit2.http.GET

interface UserService {
    @GET("/users/:id")
    suspend fun getUserById(id: Int): BaseResponse<UserResponse>
}
