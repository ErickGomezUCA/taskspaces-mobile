package com.ucapdm2025.taskspaces.ui.screens.workspace

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucapdm2025.taskspaces.data.model.Workspace
import com.ucapdm2025.taskspaces.ui.components.general.Container
import com.ucapdm2025.taskspaces.ui.components.general.FeedbackIcon
import com.ucapdm2025.taskspaces.ui.components.workspace.ProjectCard
import com.ucapdm2025.taskspaces.ui.components.workspace.UserCard
import com.ucapdm2025.taskspaces.ui.theme.*

/**
 * Composable that renders a workspace screen with sections for projects and members.
 *
 * @param workspaceName The name of the workspace, shown as the screen title.
 */
@Composable
fun WorkspaceScreen(
    workspaceId: Int
) {
    val viewModel: WorkspaceViewModel = viewModel(factory = WorkspaceViewModelFactory(workspaceId))

    // TODO: I'm using mock data for now, but this will be replaced with real data from an API.
    // I'll connect it through a ViewModel and Repository once the backend is ready.

    val workspace = viewModel.workspace.collectAsStateWithLifecycle()

    val projectNames = listOf("Project name", "Project name", "Project name", "Project name")
    val users = listOf("Username", "Username", "Username")

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
            Container(title = "Projects", showOptionsButton = true) {
                val chunkedProjects = projectNames.chunked(2)

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
                            rowItems.forEach { name ->
                                ProjectCard(
                                    name = name,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            if (rowItems.size < 2) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }


                // "Create new project" button
                TextButton(
                    onClick = { /* // TODO: I shouldn't handle this logic directly here in the Composable.
                    //  This should be delegated to the ViewModel to follow proper architecture practices. */
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    Text(
                        text = "Create new project  +",
                        style = OutfitTypography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
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

            Container(title = "Members", showOptionsButton = true) {
                val chunkedUsers = users.chunked(3)

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
                            rowItems.forEach { username ->
                                UserCard(
                                    username,
                                    modifier = Modifier
                                        .width(80.dp)
                                        .fillMaxHeight()
                                )
                            }

                            repeat(3 - rowItems.size) {
                                Spacer(modifier = Modifier.width(70.dp))
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
 * A preview composable for the [WorkspaceScreen] component.
 *
 * Displays the entire workspace screen with mock data and full system UI for design-time inspection.
 * Includes light and dark theme variants to test visual consistency across modes.
 */
@Preview(showBackground = true)
@Composable
fun WorkspaceScreenPreviewLight() {
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
//            WorkspaceScreen(workspaceName = "Workspace")
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun WorkspaceScreenPreviewDark() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
//            WorkspaceScreen(workspaceName = "Workspace")
        }
    }
}


