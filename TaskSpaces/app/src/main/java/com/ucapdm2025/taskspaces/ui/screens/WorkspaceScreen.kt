package com.ucapdm2025.taskspaces.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ucapdm2025.taskspaces.ui.components.workspace.ProjectCard
import com.ucapdm2025.taskspaces.ui.components.workspace.UserCard
import com.ucapdm2025.taskspaces.ui.theme.*
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = ExtendedTheme.colors.background05,
                shape = RoundedCornerShape(size = 24.dp)
            )
            .padding(16.dp)
    ) {
        // Workspace title
        Text(
            text = workspaceName,
            style = OutfitTypography.headlineSmall,
            color = Black100
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Projects Section
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Black05),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Projects", style = OutfitTypography.titleSmall, color = Black100)
                    TextButton(
                        onClick = { /* TODO: This will become a button to open a menu (e.g., dropdown for project actions). */ },
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("...", style = OutfitTypography.titleSmall, color = Black100)
                    }

                }

                Spacer(modifier = Modifier.height(12.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(projectNames) { name ->
                        ProjectCard(name)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // "Create new project" button
                Button(
                    onClick = { /* // TODO: I shouldn't handle this logic directly here in the Composable.
                    //  This should be delegated to the ViewModel to follow proper architecture practices. */ },
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
                    //  This should be delegated to the ViewModel to follow proper architecture practices. */ },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryLight100),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("See more", color = White100, style = OutfitTypography.bodySmall)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Members Section
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Black05),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Members", style = OutfitTypography.titleSmall, color = Black100)

                    TextButton(
                        onClick = { /* TODO: This will become a button to open a menu (e.g., dropdown for member actions). */ },
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("...", style = OutfitTypography.titleSmall, color = Black100)
                    }
                }


                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    users.forEach { username ->
                        UserCard(username)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* // TODO: I shouldn't handle this logic directly here in the Composable.
                    //  This should be delegated to the ViewModel to follow proper architecture practices. */ },
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

