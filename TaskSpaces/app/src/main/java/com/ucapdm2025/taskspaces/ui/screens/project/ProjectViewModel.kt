package com.ucapdm2025.taskspaces.ui.screens.project

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.model.ProjectModel
import com.ucapdm2025.taskspaces.data.model.TaskModel
import com.ucapdm2025.taskspaces.data.repository.memberRole.MemberRoleRepository
import com.ucapdm2025.taskspaces.data.repository.project.ProjectRepository
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepository
import kotlinx.coroutines.flow.first
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepositoryImpl
import com.ucapdm2025.taskspaces.helpers.Resource
import com.ucapdm2025.taskspaces.helpers.UiState
import com.ucapdm2025.taskspaces.helpers.friendlyMessage
import com.ucapdm2025.taskspaces.ui.components.projects.StatusVariations
import com.ucapdm2025.taskspaces.ui.components.workspace.MemberRoles
import com.ucapdm2025.taskspaces.ui.screens.workspace.UiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
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
    private val projectRepository: ProjectRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val taskRepository: TaskRepository
) : ViewModel() {
    private val _project: MutableStateFlow<UiState<ProjectModel>> = MutableStateFlow(UiState.Loading)
    val project: StateFlow<UiState<ProjectModel>> = _project.asStateFlow()

    private val _tasks: MutableStateFlow<List<TaskModel>> = MutableStateFlow(emptyList())
    val tasks: StateFlow<List<TaskModel>> = _tasks.asStateFlow()

    private val _showTaskDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showTaskDialog: StateFlow<Boolean> = _showTaskDialog.asStateFlow()

    private val _selectedTaskId: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectedTaskId: StateFlow<Int> = _selectedTaskId.asStateFlow()

    init {
        viewModelScope.launch {
            projectRepository.getProjectById(projectId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                    }

                    is Resource.Success -> {
                        val project = resource.data
                    }

                    is Resource.Error -> {
                    }
                }
            }
        }

        viewModelScope.launch {
            taskRepository.getTasksByProjectId(projectId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        // Handle loading state if necessary
                    }

                    is Resource.Success -> {
                        val tasks = resource.data
                        _tasks.value = tasks
                    }

                    is Resource.Error -> {
                    }
                }
            }
        }
    }

    // Add more fields here
    fun createTask(
        title: String,
        description: String? = null,
        deadline: LocalDateTime? = null,
        timer: Float? = null,
        status: StatusVariations = StatusVariations.PENDING,
    ) {
        viewModelScope.launch {
            val response = taskRepository.createTask(
                title,
                description,
                deadline,
                timer,
                status,
                projectId
            )

            if (response.isSuccess) {
                _selectedTaskId.value = response.getOrThrow().id
            } else {
                // Handle error, e.g., show a message to the user
                val exception = response.exceptionOrNull()
                if (exception != null) {
                    // Log or handle the exception as needed
                    Log.e("ProjectViewModel", "Error creating task: ${exception.message}")
                }
            }
        }
    }

    fun deleteTask(id: Int) {
        viewModelScope.launch {
            val response = taskRepository.deleteTask(id)

            if (!response.isSuccess) {
                // Handle error, e.g., show a message to the user
                val exception = response.exceptionOrNull()
                if (exception != null) {
                    // Log or handle the exception as needed
                    Log.e("ProjectViewModel", "Error deleting task: ${exception.message}")
                }
            }

        }
    }
    fun reloadTasks() {
        viewModelScope.launch {
            taskRepository.getTasksByProjectId(projectId).collect { resource ->
                when (resource) {
                    is Resource.Success -> _tasks.value = resource.data
                    is Resource.Error, null -> _tasks.value = emptyList()
                    else -> {}
                }
            }
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

    suspend fun hasSufficientPermissions(
        minimumRole: MemberRoles
    ): Boolean {
        return memberRoleRepository.hasSufficientPermissions(
            projectId = projectId,
            minimumRole = minimumRole
        ).firstOrNull { it is Resource.Success || it is Resource.Error }?.let { resource ->
            when (resource) {
                is Resource.Success -> resource.data == true
                else -> false
            }
        } == true
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
    private val memberRoleRepository: MemberRoleRepository,
    private val taskRepository: TaskRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProjectViewModel(projectId, projectRepository, memberRoleRepository, taskRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}