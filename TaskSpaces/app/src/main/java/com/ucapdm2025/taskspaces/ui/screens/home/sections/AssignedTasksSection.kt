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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ucapdm2025.taskspaces.data.model.TagModel
import com.ucapdm2025.taskspaces.data.model.TaskModel
import com.ucapdm2025.taskspaces.ui.components.projects.TaskCard
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

/**
 * A section that displays a list of tasks assigned to the user.
 *
 * Each task is shown using [TaskCard], with associated colored tags and a "See more" button.
 *
 * @param assignedTasks A list of [TaskModel] representing the tasks assigned to the user.
 */
@Composable
fun AssignedTasksSection(
    modifier: Modifier = Modifier,
    assignedTasks: List<TaskModel> = emptyList<TaskModel>(),
    onAssignedTaskClick: (projectId: Int, taskId: Int) -> Unit = { projectId, taskId -> }
) {
    Column(modifier = modifier.fillMaxWidth()) {
        if (assignedTasks.isNotEmpty()){
            assignedTasks.forEach { task ->
                TaskCard(
                    taskId = task.id,
                    breadcrumb = task.breadcrumb,
                    title = task.title,
                    tags = task.tags,
                    onDeleteClick = {},
                    onClick = { onAssignedTaskClick(task.projectId, task.id) },
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "No assigned tasks found...", color = ExtendedTheme.colors.onBackground50)
            }
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
