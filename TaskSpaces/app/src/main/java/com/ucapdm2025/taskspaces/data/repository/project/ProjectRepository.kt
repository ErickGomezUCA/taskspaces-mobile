package com.ucapdm2025.taskspaces.data.repository.project

import com.ucapdm2025.taskspaces.data.model.Project
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {
    fun getProjects(): Flow<List<Project>>
    suspend fun getProjectById(id: Int): Project?
    suspend fun createProject(project: Project): Project
    suspend fun updateProject(project: Project): Project
    suspend fun deleteProject(id: Int): Boolean
}