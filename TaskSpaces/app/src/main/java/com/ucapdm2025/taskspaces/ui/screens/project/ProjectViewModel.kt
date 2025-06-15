package com.ucapdm2025.taskspaces.ui.screens.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.model.ProjectModel
import com.ucapdm2025.taskspaces.data.model.TaskModel
import com.ucapdm2025.taskspaces.data.repository.project.ProjectRepository
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepository
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepositoryImpl
import com.ucapdm2025.taskspaces.helpers.Resource
import com.ucapdm2025.taskspaces.ui.components.projects.StatusVariations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

/**
 * ViewModel for managing a single project's data and its associated tasks.
 *
 * Handles loading the project and its tasks, and provides methods to create and delete tasks.
 *
 * @param projectId The unique identifier for the project to be managed.
 */
class ProjectViewModel(
    private val projectId: Int,
    private val projectRepository: ProjectRepository
) : ViewModel() {
    private val taskRepository: TaskRepository = TaskRepositoryImpl()

    private val _project: MutableStateFlow<ProjectModel?> = MutableStateFlow(null)
    val project: StateFlow<ProjectModel?> = _project

    private val _tasks: MutableStateFlow<List<TaskModel>> = MutableStateFlow(emptyList())
    val tasks: StateFlow<List<TaskModel>> = _tasks

    private val _showTaskDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showTaskDialog: StateFlow<Boolean> = _showTaskDialog

    private val _selectedTaskId: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectedTaskId: StateFlow<Int> = _selectedTaskId

    init {
        viewModelScope.launch {
            projectRepository.getProjectById(projectId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        // Handle loading state if necessary
                    }

                    is Resource.Success -> {
                        val project = resource.data
                        _project.value = project
                    }

                    is Resource.Error -> {
                        // Handle error state if necessary
                    }
                }

            }
        }

        viewModelScope.launch {
            taskRepository.getTasksByProjectId(projectId).collect { tasks ->
                _tasks.value = tasks
            }
        }
    }

    // Add more fields here
    fun createTask(title: String, description: String? = "", status: StatusVariations, projectId: Int) {
        viewModelScope.launch {
            val newTask = taskRepository.createTask(
                title = title,
                description = description ?: "",
                deadline = LocalDateTime.now(),
                status = status,
                breadcrumb = "Workspace 1 / ${project.value?.title}",
                projectId = projectId
            )

            _selectedTaskId.value = newTask.id
        }
    }

    fun deleteTask(id: Int) {
        viewModelScope.launch {
            taskRepository.deleteTask(id)
        }
    }

//    Dialog functions
    fun showTaskDialog() {
        _showTaskDialog.value = true
    }

    fun hideTaskDialog() {
        _showTaskDialog.value = false
    }

    fun setSelectedTaskId(id: Int) {
        _selectedTaskId.value = id
    }
}

/**
 * Factory for creating instances of [ProjectViewModel] with a specific projectId.
 *
 * @param projectId The unique identifier for the project.
 * @param projectRepository The repository for managing project data.
 */
class ProjectViewModelFactory(
    private val projectId: Int,
    private val projectRepository: ProjectRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProjectViewModel(projectId, projectRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}