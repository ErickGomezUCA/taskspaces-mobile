package com.ucapdm2025.taskspaces.data.repository.project

import com.ucapdm2025.taskspaces.data.model.ProjectModel
import com.ucapdm2025.taskspaces.helpers.Resource
import kotlinx.coroutines.flow.Flow

/**
 * ProjectRepository is an interface that defines the methods for managing projects in the application.
 * It provides methods to get projects by workspace ID, get a project by its ID, create a new project,
 * update an existing project, and delete a project.
 */
interface ProjectRepository {
    fun getProjectsByWorkspaceId(workspaceId: Int): Flow<Resource<List<ProjectModel>>>
    suspend fun getProjectById(id: Int): Flow<Resource<ProjectModel?>>
    suspend fun createProject(title: String, icon: String, workspaceId: Int): Result<ProjectModel>
    suspend fun updateProject(id: Int, title: String, icon: String): Result<ProjectModel>
    suspend fun deleteProject(id: Int): Result<ProjectModel>
}