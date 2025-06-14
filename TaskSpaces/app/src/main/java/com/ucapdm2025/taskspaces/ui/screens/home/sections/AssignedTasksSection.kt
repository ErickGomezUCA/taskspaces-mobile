package com.ucapdm2025.taskspaces.ui.screens.home.sections

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ucapdm2025.taskspaces.ui.components.projects.TaskCard
import com.ucapdm2025.taskspaces.ui.components.general.Tag
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

/**
 * A section that displays a list of tasks assigned to the user.
 *
 * Each task is shown using [TaskCard], with associated colored tags and a "See more" button.
 */
@Composable
fun AssignedTasksSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        TaskCard(
            title = "Task Title",
            tags = listOf(
                Tag("Tag", Color.Red),
                Tag("Tag", Color.Blue)
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        TaskCard(
            title = "Task Title",
            tags = listOf(
                Tag("Tag", Color(0xFFE67E22)),
                Tag("Tag", Color(0xFF27AE60))
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        TaskCard(
            title = "Task Title",
            tags = listOf(
                Tag("Tag", Color(0xFF9B59B6)),
                Tag("Tag", Color(0xFF3498DB))
            )
        )
        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { /* See more tasks */ },
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
 * A preview composable for [AssignedTasksSection] in light theme.
 */
@Preview(showBackground = true)
@Composable
fun AssignedTasksSectionPreviewLight() {
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            Surface {
                AssignedTasksSection()
            }
        }
    }
}

/**
 * A preview composable for [AssignedTasksSection] in dark theme.
 */
@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun AssignedTasksSectionPreviewDark() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            Surface {
                AssignedTasksSection()
            }
        }
    }
}
