package com.ucapdm2025.taskspaces.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.model.Task
import com.ucapdm2025.taskspaces.data.model.Workspace
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepository
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(): ViewModel() {
    private val workspaceRepository: WorkspaceRepository = WorkspaceRepositoryImpl()

    val workspaces: StateFlow<List<Workspace>> = workspaceRepository
        .getWorkspaces()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val workspacesSharedWithMe: StateFlow<List<Workspace>> = workspaceRepository
        .getWorkspacesSharedWithMe()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val assignedTasks: StateFlow<List<Task>> = workspaceRepository.getAssignedTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun createWorkspace(workspace: Workspace) {
        viewModelScope.launch {
            workspaceRepository.createWorkspace(workspace)
        }
    }

    fun updateWorkspace(workspace: Workspace) {
        viewModelScope.launch {
            workspaceRepository.updateWorkspace(workspace)
        }
    }

    fun deleteWorkspace(id: Int) {
        viewModelScope.launch {
            workspaceRepository.deleteWorkspace(id)
        }
    }
}