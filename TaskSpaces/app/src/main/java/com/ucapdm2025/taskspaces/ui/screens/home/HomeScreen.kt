package com.ucapdm2025.taskspaces.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
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
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
) {
    val workspaces = viewModel.workspaces.collectAsStateWithLifecycle()
//    val workspacesSharedWithMe = viewModel.workspacesSharedWithMe.collectAsStateWithLifecycle()
//    val assignedTasks = viewModel.assignedTasks.collectAsStateWithLifecycle()
    val showCreateWorkspaceDialog =
        viewModel.showCreateWorkspaceDialog.collectAsStateWithLifecycle()
    val createWorkspaceDialogData =
        viewModel.createWorkspaceDialogData.collectAsStateWithLifecycle()

//    Create workspace dialog
    if (showCreateWorkspaceDialog.value) {
        AlertDialog(
            onDismissRequest = { viewModel.hideDialog() },
            title = { Text(text = "Create a new workspace") },
            text = {
                Column {
                    TextField(
                        value = createWorkspaceDialogData.value,
                        onValueChange = { viewModel.setCreateWorkspaceDialogData(it) },
                        label = { Text(text = "Workspace Title") },
                        placeholder = { Text(text = "Enter workspace title") }
                    )
                }
            },
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
                            viewModel.createWorkspace(
                                title = createWorkspaceDialogData.value,
                            )
                            viewModel.hideDialog()
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                    ) { Text(text = "Save") }
                }
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
                        onClick = {}),
                    DropdownMenuOption(
                        label = "Update",
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Sync,
                                contentDescription = "Edit icon"
                            )
                        },
                        onClick = {})
                )
            ) {
                YourWorkspacesSection(
                    workspaces = workspaces.value,
                    onCreateWorkspaceClick = { viewModel.showDialog() })
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
