package com.ucapdm2025.taskspaces.data

import android.content.Context
import com.ucapdm2025.taskspaces.data.database.AppDatabase
import com.ucapdm2025.taskspaces.data.remote.RetrofitInstance
import com.ucapdm2025.taskspaces.data.repository.user.UserRepository
import com.ucapdm2025.taskspaces.data.repository.user.UserRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepository
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepositoryImpl

class AppProvider(context: Context) {
    private val appDatabase = AppDatabase.getDatabase(context)
//    Users
    private val userDao = appDatabase.userDao()
    private val userService = RetrofitInstance.userService
    private val userRepository: UserRepository = UserRepositoryImpl(userDao, userService)

//    Workspace
    private val workspaceDao = appDatabase.workspaceDao()
    private val workspaceService = RetrofitInstance.workspaceService
    private val workspaceRepository: WorkspaceRepository = WorkspaceRepositoryImpl(workspaceDao, workspaceService)

    fun provideWorkspaceRepository(): WorkspaceRepository {
        return workspaceRepository
    }

    fun provideUserRepository(): UserRepository {
        return userRepository
    }
}