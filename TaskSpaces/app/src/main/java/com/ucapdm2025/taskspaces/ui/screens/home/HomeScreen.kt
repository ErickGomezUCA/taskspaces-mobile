package com.ucapdm2025.taskspaces.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucapdm2025.taskspaces.data.model.WorkspaceModel
import com.ucapdm2025.taskspaces.helpers.UiState
import com.ucapdm2025.taskspaces.ui.components.general.Container
import com.ucapdm2025.taskspaces.ui.components.general.DropdownMenuOption
import com.ucapdm2025.taskspaces.ui.components.general.FeedbackIcon
import com.ucapdm2025.taskspaces.ui.components.general.FloatingStatusDialog
import com.ucapdm2025.taskspaces.ui.components.general.NotificationHost
import com.ucapdm2025.taskspaces.ui.components.home.HomeEditMode
import com.ucapdm2025.taskspaces.ui.screens.home.sections.AssignedTasksSection
import com.ucapdm2025.taskspaces.ui.screens.home.sections.SharedWorkspacesSection
import com.ucapdm2025.taskspaces.ui.screens.home.sections.YourWorkspacesSection
import com.ucapdm2025.taskspaces.ui.screens.workspace.UiEvent
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest


/**
 * A composable function that represents the main home screen of the app.
 * Sections are visually grouped using the [Container] composable,
 * and styled with themed colors via [ExtendedTheme] and [MaterialTheme].
 *
 * @param onNavigateWorkspace A lambda function to navigate to a specific workspace by its ID.
 */
@Composable
fun HomeScreen(
    onNavigateWorkspace: (Int) -> Unit,
    onAssignedTaskClick: (projectId: Int, taskId: Int) -> Unit = { projectId, taskId -> },
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
) {
    val workspaces = viewModel.workspaces.collectAsStateWithLifecycle()
    val workspacesSharedWithMe = viewModel.workspacesSharedWithMe.collectAsStateWithLifecycle()
    val assignedTasks = viewModel.assignedTasks.collectAsStateWithLifecycle()
    val showWorkspaceDialog = viewModel.showWorkspaceDialog.collectAsStateWithLifecycle()
    val workspaceDialogData = viewModel.workspaceDialogData.collectAsStateWithLifecycle()
    val editMode = viewModel.editMode.collectAsStateWithLifecycle()
    val selectedWorkspaceId = viewModel.selectedWorkspaceId.collectAsStateWithLifecycle()
    val notificationState = remember { mutableStateOf<UiEvent?>(null) }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { evt ->
            notificationState.value = evt
            delay(3000)
            notificationState.value = null
        }
    }

    val isTitleValid = workspaceDialogData.value.trim().isNotEmpty()
    val wasCreateAttempted = viewModel.wasCreateAttempted.collectAsStateWithLifecycle()

    if (showWorkspaceDialog.value) {
        AlertDialog(
            onDismissRequest = {
                viewModel.hideDialog()
                viewModel.setCreateAttempted(false) // reset
            },
            title = {
                Text(
                    text = if (editMode.value == HomeEditMode.UPDATE)
                        "Update workspace" else "Create a new workspace"
                )
            },
            text = {
                Column {
                    // Title text field
                    TextField(
                        value = workspaceDialogData.value,
                        onValueChange = { viewModel.setWorkspaceDialogData(it) },
                        label = { Text(text = "Workspace Title") },
                        placeholder = { Text(text = "Enter workspace title") },
                        isError = wasCreateAttempted.value && workspaceDialogData.value.isBlank(),
                        supportingText = {
                            if (wasCreateAttempted.value && workspaceDialogData.value.isBlank()) {
                                Text(
                                    "Workspace title cannot be empty",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    )
                }
            },

            // Group both actions buttons in the same space (do not confuse with confirmButton)
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            viewModel.hideDialog()
                            viewModel.setCreateAttempted(false) // reset
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                    ) { Text(text = "Cancel") }

                    Button(
                        onClick = {
                            viewModel.setCreateAttempted(true)

                            // Validate before continuing
                            if (workspaceDialogData.value.isBlank()) return@Button

                            if (editMode.value == HomeEditMode.UPDATE) {
                                // selectedWorkspaceId comes when the user selects a workspace to update on update mode
                                viewModel.updateWorkspace(
                                    selectedWorkspaceId.value ?: 0,
                                    workspaceDialogData.value
                                )
                                viewModel.setSelectedWorkspaceId(null)
                            } else {
                                viewModel.createWorkspace(workspaceDialogData.value)
                            }

                            viewModel.setEditMode(HomeEditMode.NONE)
                            viewModel.hideDialog()
                            viewModel.setCreateAttempted(false) // reset after success
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = if (editMode.value == HomeEditMode.UPDATE) "Update" else "Create")
                    }
                }
            }
        )
    }

//    Using a box to place this floating status dialog on top of the LazyColumn
//    This floating status dialog shows the current edit mode for Home Screen
    Box(modifier = Modifier.fillMaxSize()) {
        NotificationHost(
            event = notificationState.value,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        )
        if (editMode.value != HomeEditMode.NONE) {
            FloatingStatusDialog(
                onClose = { viewModel.setEditMode(HomeEditMode.NONE) },
                message = when (editMode.value) {
                    HomeEditMode.DELETE -> "On Delete Mode"
                    HomeEditMode.UPDATE -> "On Update Mode"
                    else -> "Invalid Mode"
                }
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            item {
                Container(
                    title = "Your workspaces",
                    dropdownMenuOptions = listOf(
                        DropdownMenuOption(
                            label = "Delete",
                            icon = {
                                Icon(
                                    imageVector = Icons.Outlined.Delete,
                                    contentDescription = "Delete icon"
                                )
                            },
                            onClick = { viewModel.setEditMode(HomeEditMode.DELETE) }
                        ),
                        DropdownMenuOption(
                            label = "Update",
                            icon = {
                                Icon(
                                    imageVector = Icons.Outlined.Sync,
                                    contentDescription = "Edit icon"
                                )
                            },
                            onClick = { viewModel.setEditMode(HomeEditMode.UPDATE) }
                        )
                    )
                ) {
                    when (val state = workspaces.value) {
                        is UiState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        is UiState.Error -> {
                            FeedbackIcon(
                                icon = Icons.Outlined.Close,
                                title = state.message ?: "Sorry, we couldn't load your workspaces."
                            )
                        }

                        is UiState.Success -> {
                            YourWorkspacesSection(
                                workspaces = state.data,
                                onClickWorkspaceCard = { workspace ->
//                            Set the selected workspace ID when in update mode and show the dialog
                                    when (editMode.value) {
                                        HomeEditMode.UPDATE -> {
                                            viewModel.setSelectedWorkspaceId(workspace.id)
                                            viewModel.setWorkspaceDialogData(workspace.title)
                                            viewModel.showDialog()
                                        }

                                        //                                Delete the workspace clicked when in delete mode
//                                TODO: Add a confirmation dialog before deleting
                                        HomeEditMode.DELETE -> {
                                            viewModel.deleteWorkspace(workspace.id)
                                            viewModel.setEditMode(HomeEditMode.NONE)
                                        }

                                        else -> onNavigateWorkspace(workspace.id)
                                    }
                                },
                                onCreateWorkspaceClick = { viewModel.showDialog() },
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // TODO: Add action to leave a workspace
            item {
//            TODO: Add action to leave a workspace
                Container(title = "Workspaces shared with me") {
                    when (val state = workspacesSharedWithMe.value) {
                        is UiState.Loading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(80.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        is UiState.Error -> {
                            FeedbackIcon(
                                icon = Icons.Outlined.Close,
                                title = state.message
                                    ?: "Sorry, we couldn't load shared workspaces."
                            )
                        }

                        is UiState.Success<List<WorkspaceModel>> -> {
                            if (state.data.isEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(24.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "No shared workspaces yet.",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    )
                                }
                            } else {
                                SharedWorkspacesSection(
                                    sharedWorkspaces = state.data,
                                    onClickWorkspaceCard = { workspace ->
                                        onNavigateWorkspace(
                                            workspace.id
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }


            item {
                Container(title = "Assigned tasks") {
                    when (val state = assignedTasks.value) {
                        is UiState.Loading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(80.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        is UiState.Error -> {
                            FeedbackIcon(
                                icon = Icons.Outlined.Close,
                                title = state.message ?: "Sorry, we couldn't load assigned tasks."
                            )
                        }

                        is UiState.Success -> {
                            AssignedTasksSection(
                                assignedTasks = state.data,
                                onAssignedTaskClick = onAssignedTaskClick
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(80.dp))
            }

        }
    }
}


/**
 * Preview of the HomeScreen in light mode using theme colors.
 */
@Preview(showBackground = true)
@Composable
fun HomeScreenLightPreview() {
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            HomeScreen(onNavigateWorkspace = {})
        }
    }
}


/**
 * Preview of the HomeScreen in dark mode using theme colors.
 */
@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun HomeScreenDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            HomeScreen(onNavigateWorkspace = {})
        }
    }
}
