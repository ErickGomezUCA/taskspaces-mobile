package com.ucapdm2025.taskspaces.ui.screens.workspace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.model.Project
import com.ucapdm2025.taskspaces.data.model.Workspace
import com.ucapdm2025.taskspaces.data.repository.project.ProjectRepository
import com.ucapdm2025.taskspaces.data.repository.project.ProjectRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepository
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WorkspaceViewModel : ViewModel() {
    val workspaceRepository: WorkspaceRepository = WorkspaceRepositoryImpl()
    val projectRepository: ProjectRepository = ProjectRepositoryImpl()
    val id = 1;

    private val _workspace = MutableStateFlow<Workspace>(
        Workspace(
            id = 0,
            title = "",
            ownerId = 0,
            createdAt = "",
            updatedAt = ""
        )
    )
    val workspace: StateFlow<Workspace> = _workspace

    private val _projects = MutableStateFlow<List<Project>>(emptyList())
    val projects: StateFlow<List<Project>> = _projects

    init {
        viewModelScope.launch {
            workspaceRepository.getWorkspaceById(id).collect {
                _workspace.value = it!!
            }
        }

//        viewModelScope.launch {
//            projectRepository.getProjects().collect { list ->
//                _projects.value = list
//            }
//        }
    }

//    fun createProject(project: Project) {
//        viewModelScope.launch {
//            projectRepository.createProject(project)
//        }
//    }

//    fun updateProject(project: Project) {
//        viewModelScope.launch {
//            projectRepository.updateProject(project)
//        }
//    }

    fun deleteProject(projectId: Int) {
        viewModelScope.launch {
            projectRepository.deleteProject(projectId)
        }
    }
}