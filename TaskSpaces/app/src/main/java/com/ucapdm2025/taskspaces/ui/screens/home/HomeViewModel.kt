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
import com.ucapdm2025.taskspaces.data.repository.task.TaskRepository
import com.ucapdm2025.taskspaces.data.repository.workspace.WorkspaceRepository
import com.ucapdm2025.taskspaces.helpers.Resource
import com.ucapdm2025.taskspaces.helpers.UiState
import com.ucapdm2025.taskspaces.helpers.friendlyMessage
import com.ucapdm2025.taskspaces.ui.components.home.HomeEditMode
import com.ucapdm2025.taskspaces.ui.screens.workspace.UiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the Home screen, responsible for managing the state and business logic.
 */
class HomeViewModel(
    private val workspaceRepository: WorkspaceRepository,
    private val authRepository: AuthRepository,
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    private val _authUserId: MutableStateFlow<Int> = MutableStateFlow(0)

    private val _workspaces = MutableStateFlow<UiState<List<WorkspaceModel>>>(UiState.Loading)
    val workspaces: StateFlow<UiState<List<WorkspaceModel>>> = _workspaces.asStateFlow()

    private val _workspacesSharedWithMe = MutableStateFlow<UiState<List<WorkspaceModel>>>(UiState.Loading)
    val workspacesSharedWithMe: StateFlow<UiState<List<WorkspaceModel>>> = _workspacesSharedWithMe.asStateFlow()

    private val _assignedTasks = MutableStateFlow<UiState<List<TaskModel>>>(UiState.Loading)
    val assignedTasks: StateFlow<UiState<List<TaskModel>>> = _assignedTasks.asStateFlow()


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

                launch {
                    workspaceRepository.getWorkspacesByUserId(userId)
                        .collect { resource ->
                            when (resource) {
                                is Resource.Loading -> {
                                    _workspaces.value = UiState.Loading
                                }

                                is Resource.Success -> {
                                    _workspaces.value = UiState.Success(resource.data)
                                }

                                is Resource.Error -> {
                                    if (resource.message.startsWith("No workspace found")) {
                                        _workspaces.value = UiState.Success(emptyList())
                                    } else {
                                        _workspaces.value = UiState.Error(resource.message)
                                    }
                                }
                            }
                        }
                }

                launch {
                    workspaceRepository.getWorkspacesSharedWithMe().collect { resource ->
                        when (resource) {
                            is Resource.Loading -> {
                                _workspacesSharedWithMe.value = UiState.Loading
                            }

                            is Resource.Success -> {
                                _workspacesSharedWithMe.value = UiState.Success(resource.data)
                            }

                            is Resource.Error -> {
                                if (resource.message.startsWith("No shared workspaces")) {
                                    _workspacesSharedWithMe.value = UiState.Success(emptyList())
                                } else {
                                    _workspacesSharedWithMe.value = UiState.Error(resource.message)
                                }
                            }
                        }
                    }
                }

// NOTE: The loading state for assigned tasks is working correctly,
// but the data loads so quickly that the CircularProgressIndicator
// is barely visible. You may want to add a slight delay
                taskRepository.getAssignedTasks(userId).collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            _assignedTasks.value = UiState.Loading
                        }
                        is Resource.Success -> {
                            _assignedTasks.value = UiState.Success(resource.data)
                        }
                        is Resource.Error -> {
                            if (resource.message.lowercase().contains("no task")) {
                                _assignedTasks.value = UiState.Success(emptyList())
                            } else {
                                _assignedTasks.value = UiState.Error(resource.message)
                            }
                        }
                    }
                }

            }
        }
    }

    fun createWorkspace(title: String) {
        viewModelScope.launch {
            val response = workspaceRepository.createWorkspace(title)

            if (response.isSuccess) {
                _uiEvent.emit(UiEvent.Success("Workspace “$title” created"))
            } else {
                // Handle error, e.g., show a message to the user
                val exception = response.exceptionOrNull()?.localizedMessage ?: "Unable to create workspace"
                val msg = friendlyMessage(exception, "The workspace could not be created")
                _uiEvent.emit(UiEvent.Error(msg))
                // Log or handle the exception as needed
                Log.e("HomeViewModel", msg)
            }
        }
    }

    fun updateWorkspace(id: Int, title: String) {
        viewModelScope.launch {
            val response = workspaceRepository.updateWorkspace(id, title)

            if (response.isSuccess) {
                _uiEvent.emit(UiEvent.Success("Workspace updated"))
                // Handle error, e.g., show a message to the user
            } else {
                val raw = response.exceptionOrNull()?.localizedMessage ?: "Unable to update workspace"
                val msg = friendlyMessage(raw, "Could not update workspace")
                _uiEvent.emit(UiEvent.Error(msg))
                // Log or handle the exception as needed
                Log.e("HomeViewModel", msg)
            }
        }
    }

    fun deleteWorkspace(id: Int) {
        viewModelScope.launch {
            val response = workspaceRepository.deleteWorkspace(id)

            if (response.isSuccess) {
                _uiEvent.emit(UiEvent.Success("Workspace deleted"))
            } else {
                // Handle error, e.g., show a message to the user
                val raw = response.exceptionOrNull()?.localizedMessage ?: "Unable to delete workspace"
                val msg = friendlyMessage(raw, "Could not delete workspace")
                _uiEvent.emit(UiEvent.Error(msg))
                // Log or handle the exception as needed
                Log.e("HomeViewModel", msg)
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
                    application.appProvider.provideAuthRepository(),
                    application.appProvider.provideTaskRepository()
                )
            }
        }
    }
}