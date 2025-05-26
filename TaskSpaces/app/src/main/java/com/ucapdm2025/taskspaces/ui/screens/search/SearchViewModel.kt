package com.ucapdm2025.taskspaces.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepository
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepositoryImpl
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class SearchViewModel: ViewModel() {
    private val workspaceRepository: WorkspaceRepository = WorkspaceRepositoryImpl()

    val workspaces = workspaceRepository.getWorkspaces().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
//    val projects
//    val tasks
//    val users
}