package com.ucapdm2025.taskspaces.data.remote

import com.ucapdm2025.taskspaces.data.remote.interceptors.AuthInterceptor
import com.ucapdm2025.taskspaces.data.remote.services.auth.AuthService
import com.ucapdm2025.taskspaces.data.remote.services.user.UserService
import com.ucapdm2025.taskspaces.data.remote.services.workspace.WorkspaceService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
//    IMPORTANT: Include "/" at the end of the base url
//    TODO: This is the local ip of my pc, please change it to a proper ip for production
    private const val BASE_URL = "http://192.168.0.26:3000/api/"

    val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
//        TODO: Fix BuildConfig no loading
//        .addInterceptor(AuthInterceptor { BuildConfig.API_TOKEN })
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val userService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }

    val authService: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }

    val workspaceService: WorkspaceService by lazy {
        retrofit.create(WorkspaceService::class.java)
    }
}