package com.ucapdm2025.taskspaces.ui.screens.workspace

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.model.ProjectModel
import com.ucapdm2025.taskspaces.data.model.UserModel
import com.ucapdm2025.taskspaces.data.model.WorkspaceModel
import com.ucapdm2025.taskspaces.data.repository.project.ProjectRepository
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepository
import com.ucapdm2025.taskspaces.helpers.Resource
import com.ucapdm2025.taskspaces.ui.components.workspace.MemberRoles
import com.ucapdm2025.taskspaces.ui.components.workspace.WorkspaceEditMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for managing a single workspace's data, including its projects and members.
 *
 * @param workspaceId The unique identifier for the workspace to be managed.
 */
class WorkspaceViewModel(
    private val workspaceId: Int,
    private val workspaceRepository: WorkspaceRepository,
    private val projectRepository: ProjectRepository
) : ViewModel() {
    private val _workspace: MutableStateFlow<WorkspaceModel?> = MutableStateFlow(null)
    val workspace: StateFlow<WorkspaceModel?> = _workspace.asStateFlow()

    private val _projects: MutableStateFlow<List<ProjectModel>> = MutableStateFlow(emptyList())
    val projects: StateFlow<List<ProjectModel>> = _projects.asStateFlow()

    private val _members: MutableStateFlow<List<UserModel>> = MutableStateFlow(emptyList())
    val members: StateFlow<List<UserModel>> = _members.asStateFlow()

    private val _showProjectDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showProjectDialog: StateFlow<Boolean> = _showProjectDialog.asStateFlow()

    private val _projectDialogData: MutableStateFlow<String> = MutableStateFlow("")
    val projectDialogData: StateFlow<String> = _projectDialogData.asStateFlow()

    private val _editMode: MutableStateFlow<WorkspaceEditMode> =
        MutableStateFlow(WorkspaceEditMode.NONE)
    val editMode: StateFlow<WorkspaceEditMode> = _editMode.asStateFlow()

    private val _selectedProjectId: MutableStateFlow<Int?> = MutableStateFlow(null)
    val selectedProjectId: StateFlow<Int?> = _selectedProjectId.asStateFlow()

    private val _showManageMembersDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showManageMembersDialog: StateFlow<Boolean> = _showManageMembersDialog.asStateFlow()

    init {
        viewModelScope.launch {
            workspaceRepository.getWorkspaceById(workspaceId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        // Handle loading state if necessary
                    }

                    is Resource.Success -> {
                        val workspace = resource.data
                        _workspace.value = workspace
                    }

                    is Resource.Error -> {
                        // Handle error state if necessary
                    }
                }
            }
        }

        viewModelScope.launch {
            projectRepository.getProjectsByWorkspaceId(workspaceId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        // TODO: Handle loading state
                    }

                    is Resource.Success -> {
                        val projects = resource.data
                        _projects.value = projects
                    }

                    is Resource.Error -> {
                        // TODO: Handle error state
                    }
                }

            }
        }

        viewModelScope.launch {
            workspaceRepository.getMembersByWorkspaceId(workspaceId).collect { memberList ->
                _members.value = memberList
            }
        }
    }

    fun createProject(title: String, icon: String) {
        viewModelScope.launch {
            val response = projectRepository.createProject(title, icon, workspaceId)

            if (!response.isSuccess) {
                // Handle error, e.g., show a message to the user
                val exception = response.exceptionOrNull()
                if (exception != null) {
                    // Log or handle the exception as needed
                    Log.e("WorkspaceViewModel", "Error creating project: ${exception.message}")
                }
            }
        }
    }

    fun updateProject(id: Int, title: String, icon: String) {
        viewModelScope.launch {
            val response = projectRepository.updateProject(id, title, icon)

            if (!response.isSuccess) {
                // Handle error, e.g., show a message to the user
                val exception = response.exceptionOrNull()
                if (exception != null) {
                    // Log or handle the exception as needed
                    Log.e("WorkspaceViewModel", "Error updating project: ${exception.message}")
                }
            }

        }
    }

    fun deleteProject(id: Int) {
        viewModelScope.launch {
            val response = projectRepository.deleteProject(id)

            if (!response.isSuccess) {
                // Handle error, e.g., show a message to the user
                val exception = response.exceptionOrNull()
                if (exception != null) {
                    // Log or handle the exception as needed
                    Log.e("WorkspaceViewModel", "Error deleting project: ${exception.message}")
                }
            }
        }
    }

    //    Members functions
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
        _showProjectDialog.value = true
    }

    fun hideDialog() {
        _showProjectDialog.value = false
    }

    fun setProjectDialogData(data: String) {
        _projectDialogData.value = data
    }

    fun setEditMode(editMode: WorkspaceEditMode) {
        _editMode.value = editMode
    }

    fun setSelectedProjectId(projectId: Int?) {
        _selectedProjectId.value = projectId
    }

//    Manage members dialog functions
    fun showManageMembersDialog() {
        _showManageMembersDialog.value = true
    }

    fun hideManageMembersDialog() {
        _showManageMembersDialog.value = false
    }

    fun inviteMember(username: String, memberRole: MemberRoles) {
        viewModelScope.launch {
            val response = workspaceRepository.inviteMember(username, memberRole, workspaceId)

            if (!response.isSuccess) {
                // Handle error, e.g., show a message to the user
                val exception = response.exceptionOrNull()
                if (exception != null) {
                    // Log or handle the exception as needed
                    Log.e("WorkspaceViewModel", "Error inviting member: ${exception.message}")
                }
            }
        }
    }
}

// Create a separate ViewModel factory for WorkspaceViewModel
// because workspaceId needs to be passed as a parameter,
// and with companion object it was not possible to do so.
/**
 * Factory for creating instances of [WorkspaceViewModel].
 *
 * @param workspaceId The unique identifier for the workspace to be managed.
 * @param workspaceRepository The repository for managing workspace data.
 * @param projectRepository The repository for managing project data.
 */
class WorkspaceViewModelFactory(
    private val workspaceId: Int,
    private val workspaceRepository: WorkspaceRepository,
    private val projectRepository: ProjectRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkspaceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WorkspaceViewModel(workspaceId, workspaceRepository, projectRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}