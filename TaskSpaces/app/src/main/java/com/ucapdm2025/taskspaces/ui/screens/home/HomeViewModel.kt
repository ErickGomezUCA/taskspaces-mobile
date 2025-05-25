package com.ucapdm2025.taskspaces.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.model.Workspace
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val workspaceRepository: WorkspaceRepository
): ViewModel() {
    private val _workspaces: MutableStateFlow<List<Workspace>> = MutableStateFlow(emptyList())
    val workspaces = _workspaces

    init {
        loadWorkspaces()
    }

    private fun loadWorkspaces() {
        viewModelScope.launch {
            _workspaces.value = workspaceRepository.getWorkspaces()
        }
    }

    fun getWorkspaceById(id: Int): Workspace? {
        return _workspaces.value.find { it.id == id }
    }

    fun createWorkspace(workspace: Workspace) {
        viewModelScope.launch {
            workspaceRepository.createWorkspace(workspace)
            loadWorkspaces() // Refresh the list after creating a new workspace
        }
    }

    fun updateWorkspace(workspace: Workspace) {
        viewModelScope.launch {
            workspaceRepository.updateWorkspace(workspace)
            loadWorkspaces() // Refresh the list after updating a workspace
        }
    }

    fun deleteWorkspace(id: Int) {
        viewModelScope.launch {
            workspaceRepository.deleteWorkspace(id)
            loadWorkspaces() // Refresh the list after deleting a workspace
        }
    }
}