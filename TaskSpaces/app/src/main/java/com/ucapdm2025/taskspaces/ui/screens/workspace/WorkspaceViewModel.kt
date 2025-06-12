package com.ucapdm2025.taskspaces.ui.screens.workspace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.model.ProjectModel
import com.ucapdm2025.taskspaces.data.model.UserModel
import com.ucapdm2025.taskspaces.data.model.WorkspaceModel
import com.ucapdm2025.taskspaces.data.repository.project.ProjectRepository
import com.ucapdm2025.taskspaces.data.repository.project.ProjectRepositoryImpl
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepository
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for managing a single workspace's data, including its projects and members.
 *
 * @param workspaceId The unique identifier for the workspace to be managed.
 */
class WorkspaceViewModel(private val workspaceId: Int): ViewModel() {
    private val workspaceRepository: WorkspaceRepository = WorkspaceRepositoryImpl()
    private val projectRepository: ProjectRepository = ProjectRepositoryImpl()

    private val _workspace: MutableStateFlow<WorkspaceModel?> = MutableStateFlow(null)
    val workspace: StateFlow<WorkspaceModel?> = _workspace.asStateFlow()

    private val _projects: MutableStateFlow<List<ProjectModel>> = MutableStateFlow(emptyList())
    val projects: StateFlow<List<ProjectModel>> = _projects.asStateFlow()

    private val _members: MutableStateFlow<List<UserModel>> = MutableStateFlow(emptyList())
    val members: StateFlow<List<UserModel>> = _members.asStateFlow()

    private val _showCreateProjectDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showCreateProjectDialog: StateFlow<Boolean> = _showCreateProjectDialog.asStateFlow()

    private val _createProjectDialogData: MutableStateFlow<String> = MutableStateFlow("")
    val createProjectDialogData: StateFlow<String> = _createProjectDialogData.asStateFlow()

    init {
        viewModelScope.launch {
            workspaceRepository.getWorkspaceById(workspaceId).collect { workspace ->
                _workspace.value = workspace
            }
        }

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

//  Dialog functions
    fun showDialog() {
        _showCreateProjectDialog.value = true
    }

    fun hideDialog() {
        _showCreateProjectDialog.value = false
    }

    fun setCreateProjectDialogData(data: String) {
        _createProjectDialogData.value = data
    }
}

/**
 * Factory for creating instances of [WorkspaceViewModel] with a specific workspaceId.
 *
 * @param workspaceId The unique identifier for the workspace.
 */
class WorkspaceViewModelFactory(private val workspaceId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkspaceViewModel::class.java)) {
            return WorkspaceViewModel(workspaceId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}