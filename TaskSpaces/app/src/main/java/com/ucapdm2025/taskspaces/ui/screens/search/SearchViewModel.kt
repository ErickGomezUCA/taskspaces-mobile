package com.ucapdm2025.taskspaces.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.repository.project.ProjectRepository
import com.ucapdm2025.taskspaces.data.repository.project.ProjectRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepository
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepository
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepositoryImpl
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class SearchViewModel: ViewModel() {
    private val workspaceRepository: WorkspaceRepository = WorkspaceRepositoryImpl()
    private val projectRepository: ProjectRepository = ProjectRepositoryImpl()
    private val taskRepository: TaskRepository = TaskRepositoryImpl()

    val workspaces = workspaceRepository.getWorkspaces().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val projects = projectRepository.getProjects().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val tasks = taskRepository.getTasks().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
//    val users
}