package com.ucapdm2025.taskspaces.ui.screens.test.home

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.model.Task
import com.ucapdm2025.taskspaces.data.model.Workspace
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepository
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepository
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TestHomeViewModel: ViewModel() {
    val userId = 1
    private val workspaceRepository: WorkspaceRepository = WorkspaceRepositoryImpl()
    private val taskRepository: TaskRepository = TaskRepositoryImpl()

    private val _workspaces: MutableStateFlow<List<Workspace>> = MutableStateFlow(emptyList())
    val workspaces: StateFlow<List<Workspace>> = _workspaces.asStateFlow()

    private val _workspacesSharedWithMe: MutableStateFlow<List<Workspace>> = MutableStateFlow(emptyList())
    val workspacesSharedWithMe: StateFlow<List<Workspace>> = _workspacesSharedWithMe.asStateFlow()

    private val _assignedTasks: MutableStateFlow<List<Task>> = MutableStateFlow(emptyList())
    val assignedTasks: StateFlow<List<Task>> = _assignedTasks.asStateFlow()

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