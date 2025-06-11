package com.ucapdm2025.taskspaces.ui.screens.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.model.Project
import com.ucapdm2025.taskspaces.data.model.Task
import com.ucapdm2025.taskspaces.data.repository.project.ProjectRepository
import com.ucapdm2025.taskspaces.data.repository.project.ProjectRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepository
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepositoryImpl
import com.ucapdm2025.taskspaces.ui.screens.workspace.WorkspaceViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for managing a single project's data and its associated tasks.
 *
 * Handles loading the project and its tasks, and provides methods to create and delete tasks.
 *
 * @param projectId The unique identifier for the project to be managed.
 */
class ProjectViewModel(projectId: Int) : ViewModel() {
    private val projectRepository: ProjectRepository = ProjectRepositoryImpl()
    private val taskRepository: TaskRepository = TaskRepositoryImpl()

    private val _project: MutableStateFlow<Project?> = MutableStateFlow(null)
    val project: StateFlow<Project?> = _project

    private val _tasks: MutableStateFlow<List<Task>> = MutableStateFlow(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    init {
        viewModelScope.launch {
            projectRepository.getProjectById(projectId).collect { project ->
                _project.value = project
            }
        }

        viewModelScope.launch {
            taskRepository.getTasksByProjectId(projectId).collect { tasks ->
                _tasks.value = tasks
            }
        }
    }

    // Add more fields here
    fun createTask(title: String, description: String, status: String, projectId: Int) {
        viewModelScope.launch {
            taskRepository.createTask(
                title = title,
                description = description,
                deadline = "",
                status = status,
                breadcrumb = "Workspace 1 / ${project.value?.title}",
                projectId = projectId
            )
        }
    }

    fun deleteTask(id: Int) {
        viewModelScope.launch {
            taskRepository.deleteTask(id)
        }
    }
}

/**
 * Factory for creating instances of [ProjectViewModel] with a specific projectId.
 *
 * @param projectId The unique identifier for the project.
 */
class ProjectViewModelFactory(private val projectId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectViewModel::class.java)) {
            return ProjectViewModel(projectId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}