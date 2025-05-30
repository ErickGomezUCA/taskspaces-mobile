package com.ucapdm2025.taskspaces.ui.screens.test.home

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.model.Workspace
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepository
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TestHomeViewModel: ViewModel() {
    val userId = 1;
    private val workspaceRepository: WorkspaceRepository = WorkspaceRepositoryImpl()

    private val _workspaces: MutableStateFlow<List<Workspace?>> = MutableStateFlow(emptyList())
    val workspaces: StateFlow<List<Workspace?>> = _workspaces.asStateFlow()

    init {
        viewModelScope.launch {
            workspaceRepository.getWorkspacesByUserId(userId).collect { workspaceList ->
                _workspaces.value = workspaceList
            }
        }
    }
}