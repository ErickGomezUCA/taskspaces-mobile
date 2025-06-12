package com.ucapdm2025.taskspaces.data

import android.content.Context
import com.ucapdm2025.taskspaces.data.database.AppDatabase
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepository
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepositoryImpl

class AppProvider(context: Context) {
    private val appDatabase = AppDatabase.getDatabase(context)
    private val workspaceDao = appDatabase.workspaceDao()
    private val workspaceRepository: WorkspaceRepository = WorkspaceRepositoryImpl(workspaceDao)

    fun provideWorkspaceRepository(): WorkspaceRepository {
        return workspaceRepository
    }
}