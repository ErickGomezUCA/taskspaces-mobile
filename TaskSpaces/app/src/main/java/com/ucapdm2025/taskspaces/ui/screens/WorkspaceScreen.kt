package com.ucapdm2025.taskspaces.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ucapdm2025.taskspaces.ui.components.workspace.ProjectCard
import com.ucapdm2025.taskspaces.ui.components.workspace.UserCard
import com.ucapdm2025.taskspaces.ui.theme.*
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme
import com.ucapdm2025.taskspaces.ui.components.Container
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Alignment

/**
 * Composable that renders a workspace screen with sections for projects and members.
 *
 * @param workspaceName The name of the workspace, shown as the screen title.
 */
@Composable
fun WorkspaceScreen(workspaceName: String) {

    // TODO: I'm using mock data for now, but this will be replaced with real data from an API.
    // I'll connect it through a ViewModel and Repository once the backend is ready.

    val projectNames = listOf("Project name", "Project name", "Project name", "Project name")
    val users = listOf("Username", "Username", "Username")

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
                text = workspaceName,
                style = OutfitTypography.headlineSmall,
                color = Black100
            )
        }


        // Projects Section
        item {
            Container(title = "Projects", showOptionsButton = true) {
                val chunkedProjects = projectNames.chunked(2)

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    chunkedProjects.forEach { rowItems ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(
                                8.dp,
                                Alignment.CenterHorizontally
                            ),
                            modifier = Modifier.fillMaxWidth()
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
                Button(
                    onClick = { /* // TODO: I shouldn't handle this logic directly here in the Composable.
                    //  This should be delegated to the ViewModel to follow proper architecture practices. */
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = White10),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    Text(
                        text = "Create new project  +",
                        style = OutfitTypography.bodyMedium,
                        color = PrimaryLight100
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // "See more" button
                Button(
                    onClick = { /* // TODO: I shouldn't handle this logic directly here in the Composable.
                    //  This should be delegated to the ViewModel to follow proper architecture practices. */
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryLight100),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("See more", color = White100, style = OutfitTypography.bodySmall)
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
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryLight100),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Manage members", color = White100, style = OutfitTypography.bodySmall)
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
                WorkspaceScreen(workspaceName = "Design Team")
            }
        }
    }

    @Preview(showBackground = true, backgroundColor = 0xFF27272A)
    @Composable
    fun WorkspaceScreenPreviewDark() {
        TaskSpacesTheme(darkTheme = true) {
            ExtendedColors(darkTheme = true) {
                WorkspaceScreen(workspaceName = "Design Team")
            }
        }
    }


