package com.ucapdm2025.taskspaces.data.remote.services.auth

import com.ucapdm2025.taskspaces.data.remote.responses.BaseResponse
import retrofit2.http.GET

interface AuthService {
    @GET("/users/login")
    suspend fun login(email: String, password: String): BaseResponse<>

    @GET("/users/register")
    suspend fun register(fullname: String, username: String, avatar: String = "", email: String, password: String, confirmPassword: String): String
}