package com.ucapdm2025.taskspaces.data.repository.project

import com.ucapdm2025.taskspaces.data.model.ProjectModel
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {
    fun getProjectsByWorkspaceId(workspaceId: Int): Flow<List<ProjectModel>>
    suspend fun getProjectById(id: Int): Flow<ProjectModel?>
    suspend fun createProject(title: String, icon: String, workspaceId: Int): ProjectModel
    suspend fun updateProject(id: Int, title: String, icon: String, workspaceId: Int): ProjectModel
    suspend fun deleteProject(id: Int): Boolean
}