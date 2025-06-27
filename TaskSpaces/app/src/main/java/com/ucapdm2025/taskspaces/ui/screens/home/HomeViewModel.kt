package com.ucapdm2025.taskspaces.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ucapdm2025.taskspaces.TaskSpacesApplication
import com.ucapdm2025.taskspaces.data.model.TaskModel
import com.ucapdm2025.taskspaces.data.model.WorkspaceModel
import com.ucapdm2025.taskspaces.data.repository.auth.AuthRepository
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepository
import com.ucapdm2025.taskspaces.helpers.Resource
import com.ucapdm2025.taskspaces.ui.components.home.HomeEditMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the Home screen, responsible for managing the state and business logic.
 */
class HomeViewModel(
    private val workspaceRepository: WorkspaceRepository,
    private val authRepository: AuthRepository,
//    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _authUserId: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _workspaces: MutableStateFlow<List<WorkspaceModel>> = MutableStateFlow(emptyList())
    val workspaces: StateFlow<List<WorkspaceModel>> = _workspaces.asStateFlow()

    private val _workspacesSharedWithMe: MutableStateFlow<List<WorkspaceModel>> =
        MutableStateFlow(emptyList())
    val workspacesSharedWithMe: StateFlow<List<WorkspaceModel>> =
        _workspacesSharedWithMe.asStateFlow()

    private val _assignedTasks: MutableStateFlow<List<TaskModel>> = MutableStateFlow(emptyList())
    val assignedTasks: StateFlow<List<TaskModel>> = _assignedTasks.asStateFlow()

    private val _showWorkspaceDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showWorkspaceDialog: StateFlow<Boolean> = _showWorkspaceDialog.asStateFlow()

    private val _workspaceDialogData: MutableStateFlow<String> = MutableStateFlow("")
    val workspaceDialogData: StateFlow<String> = _workspaceDialogData.asStateFlow()

    private val _editMode: MutableStateFlow<HomeEditMode> = MutableStateFlow(HomeEditMode.NONE)
    val editMode: StateFlow<HomeEditMode> = _editMode.asStateFlow()

    private val _selectedWorkspaceId: MutableStateFlow<Int?> = MutableStateFlow(null)
    val selectedWorkspaceId: StateFlow<Int?> = _selectedWorkspaceId.asStateFlow()

    //    Fetch user id from auth
    init {
        viewModelScope.launch {
            authRepository.authUserId.collect { userId ->
                _authUserId.value = userId
            }
        }

        viewModelScope.launch {
            workspaceRepository.getWorkspacesByUserId(_authUserId.value).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        // TODO: Handle loading state
                    }

                    is Resource.Success -> {
                        val workspaces = resource.data
                        _workspaces.value = workspaces
                    }

                    is Resource.Error -> {
                        // TODO: Handle error state
                    }
                }
            }
        }

        viewModelScope.launch {
            workspaceRepository.getWorkspacesSharedWithMe().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        // TODO: Handle loading state
                    }

                    is Resource.Success -> {
                        val workspacesShared = resource.data
                        _workspacesSharedWithMe.value = workspacesShared
                    }

                    is Resource.Error -> {
                        // TODO: Handle error state
                    }
                }
            }
        }

//        viewModelScope.launch {
//            taskRepository.getAssignedTasks(_authUserId.value).collect { assignedTasksList ->
//                _assignedTasks.value = assignedTasksList
//            }
//        }
    }

    fun createWorkspace(title: String) {
        viewModelScope.launch {
            val response = workspaceRepository.createWorkspace(title)

            if (!response.isSuccess) {
                // Handle error, e.g., show a message to the user
                val exception = response.exceptionOrNull()
                if (exception != null) {
                    // Log or handle the exception as needed
                    Log.e("HomeViewModel", "Error creating workspace: ${exception.message}")
                }
            }
        }
    }

    fun updateWorkspace(id: Int, title: String) {
        viewModelScope.launch {
            val response = workspaceRepository.updateWorkspace(id, title)

            if (!response.isSuccess) {
                // Handle error, e.g., show a message to the user
                val exception = response.exceptionOrNull()
                if (exception != null) {
                    // Log or handle the exception as needed
                    Log.e("HomeViewModel", "Error updating workspace: ${exception.message}")
                }
            }
        }
    }

    fun deleteWorkspace(id: Int) {
        viewModelScope.launch {
            val response = workspaceRepository.deleteWorkspace(id)

            if (!response.isSuccess) {
                // Handle error, e.g., show a message to the user
                val exception = response.exceptionOrNull()
                if (exception != null) {
                    // Log or handle the exception as needed
                    Log.e("HomeViewModel", "Error deleting workspace: ${exception.message}")
                }
            }
        }
    }

    //    Dialog functions
    fun showDialog() {
        _showWorkspaceDialog.value = true
    }

    fun hideDialog() {
//        Reset dialog data when hiding the dialog
        _workspaceDialogData.value = ""
        _showWorkspaceDialog.value = false
    }

    fun setWorkspaceDialogData(data: String) {
        _workspaceDialogData.value = data
    }

    fun setEditMode(mode: HomeEditMode) {
        _editMode.value = mode
    }

    fun setSelectedWorkspaceId(id: Int?) {
        _selectedWorkspaceId.value = id
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as TaskSpacesApplication
                HomeViewModel(
                    application.appProvider.provideWorkspaceRepository(),
                    application.appProvider.provideAuthRepository()
                )
            }
        }
    }
}