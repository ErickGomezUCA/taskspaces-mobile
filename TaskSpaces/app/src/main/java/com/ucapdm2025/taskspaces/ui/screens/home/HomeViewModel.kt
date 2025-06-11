package com.ucapdm2025.taskspaces.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.model.TaskModel
import com.ucapdm2025.taskspaces.data.model.WorkspaceModel
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepository
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepository
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    val userId = 1
    private val workspaceRepository: WorkspaceRepository = WorkspaceRepositoryImpl()
    private val taskRepository: TaskRepository = TaskRepositoryImpl()

    private val _workspaces: MutableStateFlow<List<WorkspaceModel>> = MutableStateFlow(emptyList())
    val workspaces: StateFlow<List<WorkspaceModel>> = _workspaces.asStateFlow()

    private val _workspacesSharedWithMe: MutableStateFlow<List<WorkspaceModel>> =
        MutableStateFlow(emptyList())
    val workspacesSharedWithMe: StateFlow<List<WorkspaceModel>> = _workspacesSharedWithMe.asStateFlow()

    private val _assignedTasks: MutableStateFlow<List<TaskModel>> = MutableStateFlow(emptyList())
    val assignedTasks: StateFlow<List<TaskModel>> = _assignedTasks.asStateFlow()

    init {
        viewModelScope.launch {
            workspaceRepository.getWorkspacesByUserId(userId).collect { workspaceList ->
                _workspaces.value = workspaceList
            }
        }

        viewModelScope.launch {
            workspaceRepository.getWorkspacesSharedWithMe(userId).collect { sharedWorkspaceList ->
                _workspacesSharedWithMe.value = sharedWorkspaceList
            }
        }

        viewModelScope.launch {
            taskRepository.getAssignedTasks(userId).collect { assignedTasksList ->
                _assignedTasks.value = assignedTasksList
            }
        }
    }

    fun createWorkspace(title: String) {
        viewModelScope.launch {
            workspaceRepository.createWorkspace(title, userId)
        }
    }

    fun updateWorkspace(id: Int, title: String) {
        viewModelScope.launch {
            workspaceRepository.updateWorkspace(id, title, userId)
        }
    }

    fun deleteWorkspace(id: Int) {
        viewModelScope.launch {
            workspaceRepository.deleteWorkspace(id)
        }
    }
}