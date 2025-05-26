package com.ucapdm2025.taskspaces.data.repository.project

import com.ucapdm2025.taskspaces.data.dummy.projectsDummy
import com.ucapdm2025.taskspaces.data.model.Project
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProjectRepositoryImpl: ProjectRepository {
    private val projects = MutableStateFlow(projectsDummy)

    override fun getProjects(): Flow<List<Project>> {
        return projects.asStateFlow()
    }

    override suspend fun getProjectById(id: Int): Project? {
        return projects.value.find { it.id == id }
    }

    override suspend fun createProject(project: Project): Project {
        projects.value = projects.value + project
        return project
    }

    override suspend fun updateProject(project: Project): Project {
        projects.value = projects.value.map {
            if (it.id == project.id) project else it
        }
        return project
    }

    override suspend fun deleteProject(id: Int): Boolean {
        val exists = projects.value.any { it.id == id }

        if (exists) {
            projects.value = projects.value.filter { it.id != id }
        }

        return exists
    }
}