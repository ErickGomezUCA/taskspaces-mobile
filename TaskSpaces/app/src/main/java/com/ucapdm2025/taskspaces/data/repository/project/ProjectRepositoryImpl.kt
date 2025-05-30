package com.ucapdm2025.taskspaces.data.repository.project

import com.ucapdm2025.taskspaces.data.dummy.projectsDummy
import com.ucapdm2025.taskspaces.data.model.Project
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime

class ProjectRepositoryImpl: ProjectRepository {
    private val projects = MutableStateFlow(projectsDummy)

    private var autoIncrementId = projects.value.size + 1;

    override fun getProjectsByWorkspaceId(workspaceId: Int): Flow<List<Project>> {
        return projects.map {list -> list.filter {it.workspaceId == workspaceId }}
    }

    override suspend fun getProjectById(id: Int): Project? {
        return projects.value.find { it.id == id }
    }

    override suspend fun createProject(title: String, icon: String, workspaceId: Int): Project {
        val createdProject = Project(
            id = autoIncrementId++,
            title = title,
            icon = icon,
            workspaceId = workspaceId,
            createdAt = LocalDateTime.now().toString(),
            updatedAt = LocalDateTime.now().toString()
        )

        projects.value = projects.value + createdProject
        return createdProject
    }

    override suspend fun updateProject(id: Int, title: String, icon: String, workspaceId: Int): Project {
        val updatedProject = Project(
            id = id,
            title = title,
            icon = icon,
            workspaceId = workspaceId,
            createdAt = LocalDateTime.now().toString(),
            updatedAt = LocalDateTime.now().toString()
        )

        projects.value = projects.value.map {
            if (it.id == updatedProject.id) updatedProject else it
        }
        return updatedProject
    }

    override suspend fun deleteProject(id: Int): Boolean {
        val exists = projects.value.any { it.id == id }

        if (exists) {
            projects.value = projects.value.filter { it.id != id }
        }

        return exists
    }
}