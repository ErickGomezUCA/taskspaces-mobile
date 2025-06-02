package com.ucapdm2025.taskspaces.ui.screens.test.workspace

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.model.Project
import com.ucapdm2025.taskspaces.data.model.User
import com.ucapdm2025.taskspaces.data.repository.project.ProjectRepository
import com.ucapdm2025.taskspaces.data.repository.project.ProjectRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepository
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TestWorkspaceViewModel: ViewModel() {
    private val workspaceRepository: WorkspaceRepository = WorkspaceRepositoryImpl()
    private val projectRepository: ProjectRepository = ProjectRepositoryImpl()
//    This ID should be included as a parameter when the ViewModel is initialized
    val workspaceId = 1

    private val _projects: MutableStateFlow<List<Project>> = MutableStateFlow(emptyList())
    val projects: StateFlow<List<Project>> = _projects.asStateFlow()

    private val _members: MutableStateFlow<List<User>> = MutableStateFlow(emptyList())
    val members: StateFlow<List<User>> = _members.asStateFlow()

    init {
        viewModelScope.launch {
            projectRepository.getProjectsByWorkspaceId(workspaceId).collect { projectList ->
                _projects.value = projectList
            }
        }

        viewModelScope.launch {
            workspaceRepository.getMembersByWorkspaceId(workspaceId).collect { memberList ->
                _members.value = memberList
            }
        }
    }

    fun createProject(title: String, icon: String, workspaceId: Int) {
        viewModelScope.launch {
            projectRepository.createProject(title, icon, workspaceId)
        }
    }

    fun updateProject(id: Int, title: String, icon: String, workspaceId: Int) {
        viewModelScope.launch {
            projectRepository.updateProject(id, title, icon, workspaceId)
        }
    }

    fun deleteProject(id: Int) {
        viewModelScope.launch {
            projectRepository.deleteProject(id)
        }
    }

    fun addMember(username: String, memberRole: String, workspaceId: Int) {
        viewModelScope.launch {
            workspaceRepository.addMember(username, memberRole, workspaceId)
        }
    }

    fun removeMember(username: String, workspaceId: Int) {
        viewModelScope.launch {
            workspaceRepository.removeMember(username, workspaceId)
        }
    }
}