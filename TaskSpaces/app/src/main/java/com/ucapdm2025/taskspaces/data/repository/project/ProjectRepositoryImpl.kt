package com.ucapdm2025.taskspaces.data.repository.project

import com.ucapdm2025.taskspaces.data.dummy.projectsDummies
import com.ucapdm2025.taskspaces.data.model.ProjectModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime

class ProjectRepositoryImpl: ProjectRepository {
    private val projects = MutableStateFlow(projectsDummies)

    private var autoIncrementId = projects.value.size + 1;

    override fun getProjectsByWorkspaceId(workspaceId: Int): Flow<List<ProjectModel>> {
        return projects.map {list -> list.filter {it.workspaceId == workspaceId }}
    }

    override suspend fun getProjectById(id: Int): Flow<ProjectModel?> {
        return projects.value.find { it.id == id }
            ?.let { MutableStateFlow(it) }
            ?: MutableStateFlow(null)
    }

    override suspend fun createProject(title: String, icon: String, workspaceId: Int): ProjectModel {
        val createdProjectModel = ProjectModel(
            id = autoIncrementId++,
            title = title,
            icon = icon,
            workspaceId = workspaceId,
            createdAt = LocalDateTime.now().toString(),
            updatedAt = LocalDateTime.now().toString()
        )

        projects.value = projects.value + createdProjectModel
        return createdProjectModel
    }

    override suspend fun updateProject(id: Int, title: String, icon: String, workspaceId: Int): ProjectModel {
        val updatedProjectModel = ProjectModel(
            id = id,
            title = title,
            icon = icon,
            workspaceId = workspaceId,
            createdAt = LocalDateTime.now().toString(),
            updatedAt = LocalDateTime.now().toString()
        )

        projects.value = projects.value.map {
            if (it.id == updatedProjectModel.id) updatedProjectModel else it
        }
        return updatedProjectModel
    }

    override suspend fun deleteProject(id: Int): Boolean {
        val exists = projects.value.any { it.id == id }

        if (exists) {
            projects.value = projects.value.filter { it.id != id }
        }

        return exists
    }
}