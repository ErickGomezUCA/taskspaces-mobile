package com.ucapdm2025.taskspaces.ui.screens.workspace

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucapdm2025.taskspaces.TaskSpacesApplication
import com.ucapdm2025.taskspaces.ui.components.general.Container
import com.ucapdm2025.taskspaces.ui.components.general.FeedbackIcon
import com.ucapdm2025.taskspaces.ui.components.workspace.ProjectCard
import com.ucapdm2025.taskspaces.ui.components.workspace.UserCard
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import com.ucapdm2025.taskspaces.ui.theme.OutfitTypography
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

/**
 * Displays the workspace details screen, including sections for projects and members.
 *
 * Fetches workspace, project, and member data using the provided [workspaceId] and displays them
 * in a structured layout. If the workspace is not found, a feedback message is shown.
 *
 * @param workspaceId The unique identifier of the workspace to display.
 * @param onProjectCardClick Callback function to handle clicks on project cards, passing the project ID.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkspaceScreen(
    workspaceId: Int,
    onProjectCardClick: (Int) -> Unit,
    viewModel: WorkspaceViewModel = viewModel(factory = WorkspaceViewModel.Factory)
) {
    // TODO: I'm using mock data for now, but this will be replaced with real data from an API.
    // I'll connect it through a ViewModel and Repository once the backend is ready.

    val workspace = viewModel.workspace.collectAsStateWithLifecycle()
    val projects = viewModel.projects.collectAsStateWithLifecycle()
    val members = viewModel.members.collectAsStateWithLifecycle()
    val showCreateProjectDialog = viewModel.showCreateProjectDialog.collectAsStateWithLifecycle()
    val createProjectDialogData = viewModel.createProjectDialogData.collectAsStateWithLifecycle()

//    Show feedback icon if the workspace is not found
    if (workspace.value == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            FeedbackIcon(
                icon = Icons.Default.Close,
                title = "Sorry, we couldn't find this workspace.",
            )
            return
        }
    }

//    Create project dialog
    if (showCreateProjectDialog.value) {
        AlertDialog(
            onDismissRequest = { viewModel.hideDialog() },
            title = { Text(text = "Create a new project") },
            text = {
//                    TODO: Add Icon field
                Column {
                    TextField(
                        value = createProjectDialogData.value,
                        onValueChange = { viewModel.setCreateProjectDialogData(it) },
                        label = { Text(text = "Project Title") },
                        placeholder = { Text(text = "Enter project title") }
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
                        onClick = { viewModel.createProject(
                            title = createProjectDialogData.value,
                            icon = "",
                            workspaceId = workspaceId
                        )

                            viewModel.hideDialog() },
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
            .background(
                color = ExtendedTheme.colors.background05,
                shape = RoundedCornerShape(size = 24.dp)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Workspace title
        item {
            Text(
                text = workspace.value?.title ?: "",
                style = OutfitTypography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }


        // Projects Section
        item {
            Container(title = "Projects") {
                val chunkedProjects = projects.value.chunked(2)

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    chunkedProjects.forEach { rowItems ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(
                                8.dp,
                                Alignment.CenterHorizontally
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            rowItems.forEach { project ->
                                ProjectCard(
                                    name = project.title,
                                    modifier = Modifier.weight(1f),
                                    onClick = { onProjectCardClick(project.id) }
                                )
                            }
                        }
                    }
                }


                // "Create new project" button
                TextButton(
                    onClick = { viewModel.showDialog() },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                ) {
                    Text(
                        text = "Create new project",
                        style = OutfitTypography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add icon",
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .size(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // "See more" button
                Button(
                    onClick = { /* // TODO: I shouldn't handle this logic directly here in the Composable.
                    //  This should be delegated to the ViewModel to follow proper architecture practices. */
                    },
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(size = 8.dp)
                        )
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "See more",
                        style = OutfitTypography.bodyMedium,
                        color = MaterialTheme.colorScheme.background
                    )
                }
            }
        }


        // Members Section
        item {

            Container(title = "Members") {
                val chunkedUsers = members.value.chunked(3)

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    chunkedUsers.forEach { rowItems ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            rowItems.forEach { member ->
                                UserCard(
                                    member.username,
                                    modifier = Modifier
                                        .width(80.dp)
                                        .fillMaxHeight()
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* // TODO: I shouldn't handle this logic directly here in the Composable.
                    //  This should be delegated to the ViewModel to follow proper architecture practices. */
                    },
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(size = 8.dp)
                        )
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Manage members",
                        style = OutfitTypography.bodyMedium,
                        color = MaterialTheme.colorScheme.background
                    )
                }
            }
        }
    }
}

/**
 * Preview of [WorkspaceScreen] in light theme with a valid workspace ID.
 *
 * Shows the workspace screen as it would appear in light mode.
 */
@Preview(showBackground = true)
@Composable
fun WorkspaceScreenPreviewLight() {
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            WorkspaceScreen(workspaceId = 1, onProjectCardClick = {})
        }
    }
}

/**
 * Preview of [WorkspaceScreen] in light theme when the workspace is not found.
 *
 * Demonstrates the not-found state in light mode.
 */
@Preview(showBackground = true)
@Composable
fun WorkspaceScreenNotFoundPreviewLight() {
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            WorkspaceScreen(workspaceId = 0, onProjectCardClick = {})
        }
    }
}

/**
 * Preview of [WorkspaceScreen] in dark theme with a valid workspace ID.
 *
 * Shows the workspace screen as it would appear in dark mode.
 */
@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun WorkspaceScreenPreviewDark() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            WorkspaceScreen(workspaceId = 1, onProjectCardClick = {})
        }
    }
}

/**
 * Preview of [WorkspaceScreen] in dark theme when the workspace is not found.
 *
 * Demonstrates the not-found state in dark mode.
 */
@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun WorkspaceScreenNotFoundPreviewDark() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            WorkspaceScreen(workspaceId = 0, onProjectCardClick = {})
        }
    }
}


