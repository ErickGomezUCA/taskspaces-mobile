package com.ucapdm2025.taskspaces.ui.components.projects

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucapdm2025.taskspaces.data.model.TagModel
import com.ucapdm2025.taskspaces.data.model.TaskModel
import com.ucapdm2025.taskspaces.ui.components.general.Tag
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * A composable function that displays a column of tasks grouped by their status.
 *
 * This component is used to represent a task status column (e.g., Pending, In Progress, Done)
 * within a project board. It includes a header for the status, a list of task cards,
 * and a button to add a new task.
 *
 * @param status The current status category represented by this column.
 * @param tasks A list of [TaskModel] objects to be displayed under the given status.
 * @param onAddTaskClick A lambda function triggered when the "Add New Task" button is clicked.
 */
@Composable
fun TaskStatusColumn(
    status: StatusVariations,
    tasks: List<TaskModel>,
    onTaskCardClick: (Int) -> Unit,
    onAddTaskClick: () -> Unit,
) {
    val scrollState = rememberScrollState()  // Inicializamos el estado del scroll

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .background(
                color = ExtendedTheme.colors.projectColumn,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
            .width(200.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TaskStatus(status)

        tasks.forEach { task ->
            TaskCard(
                title = task.title,
                tags = task.tags,
                onClick = { onTaskCardClick(task.id) }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(
                onClick = { onAddTaskClick() },
                contentPadding = PaddingValues(0.dp),
            ) {
                Text(
                    text = "Add New Task +",
                    fontSize = 16.sp
                )
            }
        }
    }
}

/**
 * A preview composable for the [TaskStatusColumn] component.
 *
 * Displays a sample column with mock tasks and a dark background to simulate
 * how the component appears within the Projects screen.
 */
@Preview(showBackground = true)
@Composable
fun TaskStatusColumnPreviewLight() {
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            Column(
                modifier = Modifier
                    .background(Color.Gray)
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                TaskStatusColumn(
                    status = StatusVariations.PENDING,
                    tasks = listOf(
                        TaskModel(
                            id = 1,
                            breadcrumb = "",
                            title = "Design login screen",
                            description = "",
                            status = StatusVariations.PENDING,
                            projectId = 1,
                            createdAt = "",
                            updatedAt = ""
                        ),
                        TaskModel(
                            id = 2,
                            breadcrumb = "",
                            title = "Connect API",
                            description = "",
                            status = StatusVariations.PENDING,
                            projectId = 1,
                            createdAt = "",
                            updatedAt = ""
                        )
                    ),
                    onTaskCardClick = {},
                    onAddTaskClick = {},
                )
            }
        }
    }
}

/**
 * A preview composable for the [TaskStatusColumn] component using the dark theme.
 *
 * Displays a sample column with mock tasks under the "Pending" status to visualize
 * its appearance and layout in dark mode.
 */
@Preview(showBackground = true)
@Composable
fun TaskStatusColumnPreviewDark() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            Column(
                modifier = Modifier
                    .background(Color(0xFF21232B))
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                TaskStatusColumn(
                    status = StatusVariations.PENDING,
                    tasks = listOf(
                        TaskModel(
                            id = 1,
                            breadcrumb = "",
                            title = "Design login screen",
                            description = "",
                            status = StatusVariations.PENDING,
                            projectId = 1,
                            createdAt = "",
                            updatedAt = ""
                        ),
                        TaskModel(
                            id = 2,
                            breadcrumb = "",
                            title = "Connect API",
                            description = "",
                            status = StatusVariations.PENDING,
                            projectId = 1,
                            createdAt = "",
                            updatedAt = ""
                        )
                    ),
                    onTaskCardClick = {},
                    onAddTaskClick = {},
                )
            }
        }
    }
}
