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
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucapdm2025.taskspaces.ui.components.general.Container
import com.ucapdm2025.taskspaces.ui.components.general.DropdownMenuOption
import com.ucapdm2025.taskspaces.ui.components.general.FloatingStatusDialog
import com.ucapdm2025.taskspaces.ui.components.home.HomeEditMode
import com.ucapdm2025.taskspaces.ui.screens.home.sections.AssignedTasksSection
import com.ucapdm2025.taskspaces.ui.screens.home.sections.SharedWorkspacesSection
import com.ucapdm2025.taskspaces.ui.screens.home.sections.YourWorkspacesSection
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

/**
 * A composable function that represents the main home screen of the app.
 * Sections are visually grouped using the [Container] composable,
 * and styled with themed colors via [ExtendedTheme] and [MaterialTheme].
 *
 * @param onNavigateWorkspace A lambda function to navigate to a specific workspace by its ID.
 */
@Composable
fun HomeScreen(
    onNavigateWorkspace: (Int) -> Unit = {},
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
) {
    val workspaces = viewModel.workspaces.collectAsStateWithLifecycle()
//    val workspacesSharedWithMe = viewModel.workspacesSharedWithMe.collectAsStateWithLifecycle()
//    val assignedTasks = viewModel.assignedTasks.collectAsStateWithLifecycle()
    val showWorkspaceDialog =
        viewModel.showWorkspaceDialog.collectAsStateWithLifecycle()
    val workspaceDialogData =
        viewModel.workspaceDialogData.collectAsStateWithLifecycle()
    val editMode = viewModel.editMode.collectAsStateWithLifecycle()
    val selectedWorkspaceId =
        viewModel.selectedWorkspaceId.collectAsStateWithLifecycle()

//    Workspace dialog (for create and update workspace)
    if (showWorkspaceDialog.value) {
        AlertDialog(
            onDismissRequest = { viewModel.hideDialog() },
            title = { Text(text = "Create a new workspace") },
            text = {
                Column {
//                    Title text field
                    TextField(
                        value = workspaceDialogData.value,
                        onValueChange = { viewModel.setWorkspaceDialogData(it) },
                        label = { Text(text = "Workspace Title") },
                        placeholder = { Text(text = "Enter workspace title") }
                    )
                }
            },
//            Group both actions buttons in the same space (do not confuse with confirmButton)
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(
                        onClick = { viewModel.hideDialog() },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                    ) { Text(text = "Cancel") }
                    Button(
                        onClick = {
//                            Update selected workspace if in update mode, otherwise create a new one
                            if (editMode.value == HomeEditMode.UPDATE) {
//                                selectedWorkspaceId comes when the user selects a workspace to update on update mode
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
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                    ) { Text(text = if (editMode.value == HomeEditMode.UPDATE) "Update" else "Create") }
                }
            }
        )
    }

//    Using a box to place this floating status dialog on top of the LazyColumn
//    This floating status dialog shows the current edit mode for Home Screen
    Box(modifier = Modifier.fillMaxSize()) {
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
                            onClick = { viewModel.setEditMode(HomeEditMode.DELETE) }),
                        DropdownMenuOption(
                            label = "Update",
                            icon = {
                                Icon(
                                    imageVector = Icons.Outlined.Sync,
                                    contentDescription = "Edit icon"
                                )
                            },
                            onClick = { viewModel.setEditMode(HomeEditMode.UPDATE) })
                    )
                ) {
                    YourWorkspacesSection(
                        workspaces = workspaces.value,
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
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Container(title = "Workspaces shared with me") {
                    SharedWorkspacesSection()
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Container(title = "Assigned tasks") {
                    AssignedTasksSection()
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
            HomeScreen()
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
            HomeScreen()
        }
    }
}
