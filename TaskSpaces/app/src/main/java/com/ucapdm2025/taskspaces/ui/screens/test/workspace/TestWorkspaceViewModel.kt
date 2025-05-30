package com.ucapdm2025.taskspaces.ui.screens.test.workspace

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.model.Project
import com.ucapdm2025.taskspaces.data.repository.project.ProjectRepository
import com.ucapdm2025.taskspaces.data.repository.project.ProjectRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TestWorkspaceViewModel: ViewModel() {
    private val projectRepository: ProjectRepository = ProjectRepositoryImpl()
    val workspaceId = 1;

    private val _projects: MutableStateFlow<List<Project>> = MutableStateFlow(emptyList())
    val projects: StateFlow<List<Project>> = _projects.asStateFlow()

    init {
        viewModelScope.launch {
            projectRepository.getProjectsByWorkspaceId(workspaceId).collect { projectList ->
                _projects.value = projectList
            }
        }
    }

    fun createProject(title: String, icon: String) {
        viewModelScope.launch {
            projectRepository.createProject(title, icon, workspaceId)
        }
    }

    fun updateProject(id: Int, title: String, icon: String) {
        viewModelScope.launch {
            projectRepository.updateProject(id, title, icon, workspaceId)
        }
    }

    fun deleteProject(id: Int) {
        viewModelScope.launch {
            projectRepository.deleteProject(id)
        }
    }
}