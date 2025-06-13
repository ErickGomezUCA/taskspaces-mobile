package com.ucapdm2025.taskspaces.ui.screens.home.sections

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ucapdm2025.taskspaces.ui.components.home.WorkspaceCard
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

/**
 * A section that displays workspaces shared with the user.
 *
 * Includes two sample [WorkspaceCard] components and a "See more" button.
 */
@Composable
fun SharedWorkspacesSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        WorkspaceCard(name = "Workspace 3", projectsCount = 1, membersCount = 1)
        Spacer(modifier = Modifier.height(8.dp))

        WorkspaceCard(name = "Workspace 4", projectsCount = 1, membersCount = 1)
        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { /* View more shared workspaces */ },
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

/**
 * Preview of [SharedWorkspacesSection] in light mode.
 */
@Preview(showBackground = true)
@Composable
fun SharedWorkspacesSectionPreviewLight() {
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            Surface {
                SharedWorkspacesSection()
            }
        }
    }
}

/**
 * Preview of [SharedWorkspacesSection] in dark mode.
 */
@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun SharedWorkspacesSectionPreviewDark() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            Surface {
                SharedWorkspacesSection()
            }
        }
    }
}
