package com.ucapdm2025.taskspaces.ui.screens.workspace

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ucapdm2025.taskspaces.data.model.ProjectModel
import com.ucapdm2025.taskspaces.data.model.WorkspaceModel
import com.ucapdm2025.taskspaces.data.model.relational.WorkspaceMemberModel
import com.ucapdm2025.taskspaces.data.repository.memberRole.MemberRoleRepository
import com.ucapdm2025.taskspaces.data.repository.project.ProjectRepository
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepository
import com.ucapdm2025.taskspaces.helpers.Resource
import com.ucapdm2025.taskspaces.helpers.UiState
import com.ucapdm2025.taskspaces.helpers.friendlyMessage
import com.ucapdm2025.taskspaces.ui.components.workspace.MemberRoles
import com.ucapdm2025.taskspaces.ui.components.workspace.WorkspaceEditMode
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

sealed interface UiEvent {
    data class Success(val message: String) : UiEvent
    data class Error(val message: String)   : UiEvent
}
/**
 * ViewModel for managing a single workspace's data, including its projects and members.
 *
 * @param workspaceId The unique identifier for the workspace to be managed.
 */
class WorkspaceViewModel(
    private val workspaceId: Int,
    private val workspaceRepository: WorkspaceRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val projectRepository: ProjectRepository
) : ViewModel() {
    private val _workspaceState = MutableStateFlow<UiState<WorkspaceModel?>>(
        UiState.Loading
    )
    val workspaceState: StateFlow<UiState<WorkspaceModel?>> = _workspaceState.asStateFlow()



    private val _projectsState = MutableStateFlow<UiState<List<ProjectModel>>>(UiState.Loading)
    val projectsState: StateFlow<UiState<List<ProjectModel>>> = _projectsState.asStateFlow()

    private val _showProjectDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showProjectDialog: StateFlow<Boolean> = _showProjectDialog.asStateFlow()

    private val _projectDialogData: MutableStateFlow<String> = MutableStateFlow("")
    val projectDialogData: StateFlow<String> = _projectDialogData.asStateFlow()

    private val _editMode: MutableStateFlow<WorkspaceEditMode> =
        MutableStateFlow(WorkspaceEditMode.NONE)
    val editMode: StateFlow<WorkspaceEditMode> = _editMode.asStateFlow()

    private val _selectedProjectId: MutableStateFlow<Int?> = MutableStateFlow(null)
    val selectedProjectId: StateFlow<Int?> = _selectedProjectId.asStateFlow()


    private val _membersState = MutableStateFlow<UiState<List<WorkspaceMemberModel>>>(UiState.Loading)
    val membersState: StateFlow<UiState<List<WorkspaceMemberModel>>> = _membersState.asStateFlow()

    private val _showManageMembersDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showManageMembersDialog: StateFlow<Boolean> = _showManageMembersDialog.asStateFlow()

    private val _wasProjectCreateAttempted: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val wasProjectCreateAttempted: StateFlow<Boolean> = _wasProjectCreateAttempted.asStateFlow()

    fun markProjectCreateAttempted() {
        _wasProjectCreateAttempted.value = true
    }

    fun resetProjectCreateAttempted() {
        _wasProjectCreateAttempted.value = false
    }


    private val _uiEvent = MutableSharedFlow<UiEvent>()   // replay = 0 por defecto
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    private val _inviteUsername = MutableStateFlow("")
    val inviteUsername: StateFlow<String> = _inviteUsername.asStateFlow()

    private val _wasInviteAttempted = MutableStateFlow(false)
    val wasInviteAttempted: StateFlow<Boolean> = _wasInviteAttempted.asStateFlow()

    fun setInviteUsername(username: String) {
        _inviteUsername.value = username
    }

    fun setInviteAttempted(value: Boolean) {
        _wasInviteAttempted.value = value
    }

    private val _showProjectDeleteConfirmationDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showProjectDeleteConfirmationDialog: StateFlow<Boolean> = _showProjectDeleteConfirmationDialog.asStateFlow()

    private val _pendingProjectToDeleteId: MutableStateFlow<Int?> = MutableStateFlow(null)
    val pendingProjectToDeleteId: StateFlow<Int?> = _pendingProjectToDeleteId.asStateFlow()


    init {
//        Get current workspace info
        viewModelScope.launch {
            workspaceRepository.getWorkspaceById(workspaceId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _workspaceState.value = UiState.Loading
                    }

                    is Resource.Success -> {
                        _workspaceState.value = UiState.Success(resource.data)
                    }

                    is Resource.Error -> {
                        _workspaceState.value = UiState.Error(resource.message)
                    }
                }
            }
        }

//        Get projects list
        viewModelScope.launch {
            projectRepository.getProjectsByWorkspaceId(workspaceId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _projectsState.value = UiState.Loading
                    }

                    is Resource.Success -> {
                        val projects = resource.data
                        _projectsState.value = UiState.Success(resource.data)
                    }

                    is Resource.Error -> {
                        _projectsState.value = UiState.Error(resource.message)
                    }
                }
            }
        }

//        Get members list
        viewModelScope.launch {
            workspaceRepository.getMembersByWorkspaceId(workspaceId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _membersState.value = UiState.Loading
                    }

                    is Resource.Success -> {
                        _membersState.value = UiState.Success(resource.data)
                    }

                    is Resource.Error -> {
                        _membersState.value = UiState.Error(resource.message)
                    }
                }
            }
        }
    }

    fun createProject(title: String, icon: String) {
        viewModelScope.launch {
            val response = projectRepository.createProject(title, icon, workspaceId)
            if (response.isSuccess) {
                _uiEvent.emit(UiEvent.Success("Project created successfully"))
            } else {
                val raw = response.exceptionOrNull()?.localizedMessage ?: "Unable to create project"
                val msg = friendlyMessage(raw, "The project could not be created")
                _uiEvent.emit(UiEvent.Error(msg))
                Log.e("WorkspaceViewModel", "Error creating project: $msg")
            }
        }
    }

    fun updateProject(id: Int, title: String, icon: String) {
        viewModelScope.launch {
            val response = projectRepository.updateProject(id, title, icon)
            if (response.isSuccess) {
                _uiEvent.emit(UiEvent.Success("Project updated successfully"))
            } else {
                val raw = response.exceptionOrNull()?.localizedMessage ?: "Unable to update project"
                val msg = friendlyMessage(raw, "The project could not be updated.")
                _uiEvent.emit(UiEvent.Error(msg))
                Log.e("WorkspaceViewModel", "Error updating project: $msg")
            }
        }
    }

    fun deleteProject(id: Int) {
        viewModelScope.launch {
            val response = projectRepository.deleteProject(id)
            if (response.isSuccess) {
                _uiEvent.emit(UiEvent.Success("Project deleted successfully"))
            } else {
                val raw = response.exceptionOrNull()?.localizedMessage ?: "Unable to delete project"
                val msg = friendlyMessage(raw, "The project could not be deleted")
                _uiEvent.emit(UiEvent.Error(msg))
                Log.e("WorkspaceViewModel", "Error deleting project: $msg")
            }
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

        viewModelScope.launch {
            when (editMode) {
                WorkspaceEditMode.UPDATE -> _uiEvent.emit(UiEvent.Success("Update mode enabled"))
                WorkspaceEditMode.DELETE -> _uiEvent.emit(UiEvent.Success("Delete mode enabled"))
                WorkspaceEditMode.NONE   -> {} // not notification
            }
        }
    }

    fun setSelectedProjectId(projectId: Int?) {
        _selectedProjectId.value = projectId
    }

    fun showProjectDeleteConfirmationDialog(projectId: Int) {
        _pendingProjectToDeleteId.value = projectId
        _showProjectDeleteConfirmationDialog.value = true
    }

    fun hideProjectDeleteConfirmationDialog() {
        _pendingProjectToDeleteId.value = null
        _showProjectDeleteConfirmationDialog.value = false
    }

    //    Manage members dialog functions
    fun showManageMembersDialog() {
        _showManageMembersDialog.value = true
    }


    fun hideManageMembersDialog() {
        _showManageMembersDialog.value = false
    }

    fun inviteMember(username: String, memberRole: MemberRoles) {
        val trimmed = username.trim()
        if (trimmed.isEmpty()) {
            Log.e("WorkspaceViewModel", "Username input is empty")
            return
        }

        viewModelScope.launch {
            val response = workspaceRepository.inviteMember(trimmed, memberRole, workspaceId)

            Log.d("WorkspaceViewModel", "Invite member response: $response")

            if (response.isSuccess) {
                _uiEvent.emit(UiEvent.Success("Invitation sent to @$username"))
            } else {
                val raw = response.exceptionOrNull()?.localizedMessage ?: "Unable to invite member"
                val msg = friendlyMessage(raw, "Could not invite member")
                _uiEvent.emit(UiEvent.Error(msg))
                Log.e("WorkspaceViewModel", "Error inviting member: $msg")
            }
        }
    }


    fun updateMemberRole(
        userId: Int,
        newMemberRole: MemberRoles
    ) {
        viewModelScope.launch {
            val response = workspaceRepository.updateMember(
                userId = userId,
                memberRole = newMemberRole,
                workspaceId = workspaceId
            )

            Log.d("WorkspaceViewModel", "Update member role response: $response")

            if (response.isSuccess) {
                _uiEvent.emit(UiEvent.Success("Member role updated"))
            } else {
                val raw = response.exceptionOrNull()?.localizedMessage ?: "Unable to update role"
                val msg = friendlyMessage(raw, "The role could not be updated.")
                _uiEvent.emit(UiEvent.Error(msg))
                Log.e("WorkspaceViewModel", "Error updating member role: $msg")
            }
        }
    }

    fun removeMember(userId: Int) {
        viewModelScope.launch {
            val response = workspaceRepository.removeMember(
                userId = userId,
                workspaceId = workspaceId
            )

            Log.d("WorkspaceViewModel", "Remove member response: $response")

            if (response.isSuccess) {
                _uiEvent.emit(UiEvent.Success("Member removed"))
            } else {
                val raw = response.exceptionOrNull()?.localizedMessage ?: "Unable to remove member"
                val msg = friendlyMessage(raw, "Could not delete member")
                _uiEvent.emit(UiEvent.Error(msg))
                Log.e("WorkspaceViewModel", "Error removing member: $msg")
            }
        }
    }
    fun hasSufficientPermissions(
        minimumRole: MemberRoles
    ): Boolean {
        return kotlinx.coroutines.runBlocking {
            memberRoleRepository.hasSufficientPermissions(
                workspaceId = workspaceId,
                minimumRole = minimumRole
            ).firstOrNull { it is Resource.Success || it is Resource.Error }?.let { resource ->
                when (resource) {
                    is Resource.Success -> resource.data == true
                    else -> false
                }
            } == true
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
    private val memberRoleRepository: MemberRoleRepository,
    private val projectRepository: ProjectRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkspaceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WorkspaceViewModel(
                workspaceId,
                workspaceRepository,
                memberRoleRepository,
                projectRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

