package com.ucapdm2025.taskspaces.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.model.Task
import com.ucapdm2025.taskspaces.data.model.Workspace
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepository
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepository
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(): ViewModel() {
    private val workspaceRepository: WorkspaceRepository = WorkspaceRepositoryImpl()
    private val taskRepository: TaskRepository = TaskRepositoryImpl()

    private val _workspaces = MutableStateFlow<List<Workspace?>>(emptyList())
    val workspaces: StateFlow<List<Workspace?>> = _workspaces

    private val _workspacesSharedWithMe = MutableStateFlow<List<Workspace?>>(emptyList())
    val workspacesSharedWithMe: StateFlow<List<Workspace?>> = _workspacesSharedWithMe

    private val _assignedTasks = MutableStateFlow<List<Task>>(emptyList())
    val assignedTasks: StateFlow<List<Task>> = _assignedTasks

    init {
        viewModelScope.launch {
            workspaceRepository.getWorkspacesByUserId(1).collect { list ->
                _workspaces.value = list
            }
        }

        viewModelScope.launch {
            workspaceRepository.getWorkspacesSharedWithMe(1).collect { list ->
                _workspacesSharedWithMe.value = list
            }
        }

        viewModelScope.launch {
            taskRepository.getTasks().collect { list ->
                _assignedTasks.value = list
            }
        }
    }

//    fun createWorkspace(workspace: Workspace) {
//        viewModelScope.launch {
//            workspaceRepository.createWorkspace(workspace)
//        }
//    }

//    fun updateWorkspace(workspace: Workspace) {
//        viewModelScope.launch {
//            workspaceRepository.updateWorkspace(workspace)
//        }
//    }

    fun deleteWorkspace(id: Int) {
        viewModelScope.launch {
            workspaceRepository.deleteWorkspace(id)
        }
    }
}