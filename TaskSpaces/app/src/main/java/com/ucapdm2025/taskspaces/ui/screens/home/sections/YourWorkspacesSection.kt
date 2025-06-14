package com.ucapdm2025.taskspaces.ui.screens.home.sections

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucapdm2025.taskspaces.data.model.WorkspaceModel
import com.ucapdm2025.taskspaces.ui.components.home.WorkspaceCard
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

/**
 * A section that displays a list of workspaces owned by the user.
 * Each workspace is shown using [WorkspaceCard],
 * with a button to create a new workspace and a button to see more workspaces.
 *
 * @param workspaces A list of [WorkspaceModel] representing the user's workspaces.
 * @param modifier Optional [Modifier] to customize the layout.
 */
@Composable
fun YourWorkspacesSection(
    workspaces: List<WorkspaceModel>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (workspaces.isNotEmpty()) {
            workspaces.forEach { workspace ->
                WorkspaceCard(
                    name = workspace.title,
                    projectsCount = 1,
                    membersCount = 1
                )
            }
        } else {
            Column(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "No workspaces found...", color = ExtendedTheme.colors.onBackground50)
            }
        }

        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            // Create workspace button
            TextButton(onClick = { /* Create workspace action */ }, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Create workspace +",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // See more button
            Button(
                onClick = { /* Navigate to full list */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("See more")
            }
        }
    }
}

/**
 * Preview of [YourWorkspacesSection] in light theme.
 */
@Preview(showBackground = true)
@Composable
fun YourWorkspacesSectionPreviewLight() {
    val workspaces: List<WorkspaceModel> = listOf(
        WorkspaceModel(
            id = 1,
            title = "Workspace 1",
            ownerId = 1,
            createdAt = "2023-01-01",
            updatedAt = "2023-01-02"
        ),
        WorkspaceModel(
            id = 2,
            title = "Workspace 2",
            ownerId = 1,
            createdAt = "2023-01-03",
            updatedAt = "2023-01-04"
        )
    )

    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            Surface {
                YourWorkspacesSection(workspaces = workspaces)
            }
        }
    }
}

/**
 * Preview of [YourWorkspacesSection] in light theme with no workspaces.
 */
@Preview(showBackground = true)
@Composable
fun YourWorkspacesSectionNoDataPreviewLight() {
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            Surface {
                YourWorkspacesSection(workspaces = emptyList())
            }
        }
    }
}

/**
 * Preview of [YourWorkspacesSection] in dark theme.
 */
@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun YourWorkspacesSectionPreviewDark() {
    val workspaces: List<WorkspaceModel> = listOf(
        WorkspaceModel(
            id = 1,
            title = "Workspace 1",
            ownerId = 1,
            createdAt = "2023-01-01",
            updatedAt = "2023-01-02"
        ),
        WorkspaceModel(
            id = 2,
            title = "Workspace 2",
            ownerId = 1,
            createdAt = "2023-01-03",
            updatedAt = "2023-01-04"
        )
    )


    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            Surface {
                YourWorkspacesSection(workspaces = workspaces)
            }
        }
    }
}

/**
 * Preview of [YourWorkspacesSection] in dark theme with no workspaces.
 */
@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun YourWorkspacesSectionNoDataPreviewDark() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            Surface {
                YourWorkspacesSection(workspaces = emptyList())
            }
        }
    }
}
