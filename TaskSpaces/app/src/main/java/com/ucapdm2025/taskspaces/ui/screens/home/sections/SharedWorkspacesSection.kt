package com.ucapdm2025.taskspaces.ui.screens.home.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ucapdm2025.taskspaces.data.model.WorkspaceModel
import com.ucapdm2025.taskspaces.ui.components.home.WorkspaceCard
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

/**
 * A section that displays workspaces shared with the user.
 *
 * Includes two sample [WorkspaceCard] components and a "See more" button.
 *
 * @param sharedWorkspaces A list of [WorkspaceModel] representing the workspaces shared with the user.
 */
@Composable
fun SharedWorkspacesSection(
    modifier: Modifier = Modifier,
    sharedWorkspaces: List<WorkspaceModel> = emptyList<WorkspaceModel>(),
    onClickWorkspaceCard: (WorkspaceModel) -> Unit = { }
) {
    Column(modifier = modifier.fillMaxWidth()) {
        if (sharedWorkspaces.isNotEmpty()) {
            sharedWorkspaces.forEach { workspace ->
                WorkspaceCard(
                    name = workspace.title,
                    projectsCount = 1,
                    membersCount = 1,
                    isWorkspaceShared = true,
                    onClick = { onClickWorkspaceCard(workspace) },
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "No workspaces found...", color = ExtendedTheme.colors.onBackground50)
            }
        }

        if (sharedWorkspaces.size > 2) {
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
