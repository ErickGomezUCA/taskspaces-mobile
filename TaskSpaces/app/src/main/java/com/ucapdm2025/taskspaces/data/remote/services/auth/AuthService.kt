package com.ucapdm2025.taskspaces.data.remote.services.auth

import com.ucapdm2025.taskspaces.data.remote.requests.LoginRequest
import com.ucapdm2025.taskspaces.data.remote.responses.BaseResponse
import com.ucapdm2025.taskspaces.data.remote.responses.LoginResponse
import com.ucapdm2025.taskspaces.data.remote.responses.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @POST("/users/login")
    suspend fun login(@Body request: LoginRequest): BaseResponse<LoginResponse>

    @POST("/users/register")
    suspend fun register(
        fullname: String,
        username: String,
        avatar: String = "",
        email: String,
        password: String,
        confirmPassword: String
    ): BaseResponse<UserResponse>
}