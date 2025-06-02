package com.ucapdm2025.taskspaces.ui.screens.test.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.model.Project
import com.ucapdm2025.taskspaces.data.repository.project.ProjectRepository
import com.ucapdm2025.taskspaces.data.repository.project.ProjectRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TestProjectViewModel: ViewModel() {
    private val projectRepository: ProjectRepository = ProjectRepositoryImpl()

    private val projectId = 1

    private val _project: MutableStateFlow<Project?> = MutableStateFlow(null)
    val project: StateFlow<Project?> = _project

    init {
        viewModelScope.launch {
            projectRepository.getProjectById(projectId).collect { project ->
                _project.value = project
            }
        }
    }
}