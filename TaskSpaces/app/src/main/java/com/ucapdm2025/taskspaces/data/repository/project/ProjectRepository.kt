package com.ucapdm2025.taskspaces.data.repository.project

import com.ucapdm2025.taskspaces.data.model.Project
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {
    fun getProjectsByWorkspaceId(workspaceId: Int): Flow<List<Project>>
    suspend fun getProjectById(id: Int): Project?
    suspend fun createProject(title: String, icon: String, workspaceId: Int): Project
    suspend fun updateProject(id: Int, title: String, icon: String, workspaceId: Int): Project
    suspend fun deleteProject(id: Int): Boolean
}