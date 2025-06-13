package com.ucapdm2025.taskspaces.data.remote

import com.ucapdm2025.taskspaces.data.remote.interceptors.AuthInterceptor
import com.ucapdm2025.taskspaces.data.remote.services.user.UserService
import com.ucapdm2025.taskspaces.data.remote.services.workspace.WorkspaceService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://localhost:3000/api"

    val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
//        TODO: Fix BuildConfig no loading
        .addInterceptor(AuthInterceptor { BuildConfig.API_TOKEN })
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val userService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }

    val workspaceService: WorkspaceService by lazy {
        retrofit.create(WorkspaceService::class.java)
    }
}