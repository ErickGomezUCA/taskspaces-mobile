package com.ucapdm2025.taskspaces.data.remote.services

import com.ucapdm2025.taskspaces.data.remote.requests.LoginRequest
import com.ucapdm2025.taskspaces.data.remote.requests.SignUpRequest
import com.ucapdm2025.taskspaces.data.remote.responses.BaseResponse
import com.ucapdm2025.taskspaces.data.remote.responses.LoginResponse
import com.ucapdm2025.taskspaces.data.remote.responses.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * AuthService is an interface that defines the authentication-related API endpoints.
 * It includes methods for user login and registration.
 */
interface AuthService {
    @POST("users/login")
    suspend fun login(@Body request: LoginRequest): BaseResponse<LoginResponse>

    @POST("users/register")
    suspend fun register(
        @Body request: SignUpRequest
    ): BaseResponse<UserResponse>
}