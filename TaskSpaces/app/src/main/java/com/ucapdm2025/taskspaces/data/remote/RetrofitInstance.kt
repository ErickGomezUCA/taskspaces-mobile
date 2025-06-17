package com.ucapdm2025.taskspaces.data.remote

import com.ucapdm2025.taskspaces.helpers.TokenHolder
import com.ucapdm2025.taskspaces.data.remote.interceptors.AuthInterceptor
import com.ucapdm2025.taskspaces.data.remote.services.AuthService
import com.ucapdm2025.taskspaces.data.remote.services.BookmarkService
import com.ucapdm2025.taskspaces.data.remote.services.ProjectService
import com.ucapdm2025.taskspaces.data.remote.services.TaskService
import com.ucapdm2025.taskspaces.data.remote.services.UserService
import com.ucapdm2025.taskspaces.data.remote.services.WorkspaceService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * RetrofitInstance is a singleton object that provides a Retrofit instance for making API calls.
 * It includes services for user, authentication, and workspace operations.
 */
object RetrofitInstance {
    //    IMPORTANT: Include "/" at the end of the base url
//    Production backend URL
    private const val BASE_URL = "https://taskspaces-backend-ox3g.onrender.com/api/"

    val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor(AuthInterceptor { TokenHolder.token })
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

    val projectService: ProjectService by lazy {
        retrofit.create(ProjectService::class.java)
    }

    val taskService: TaskService by lazy {
        retrofit.create(TaskService::class.java)
    }

    val bookmarkService: BookmarkService by lazy {
        retrofit.create(BookmarkService::class.java)
    }
}