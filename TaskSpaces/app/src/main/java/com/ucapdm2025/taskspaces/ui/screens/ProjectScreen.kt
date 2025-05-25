package com.ucapdm2025.taskspaces.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ucapdm2025.taskspaces.ui.components.projects.ProjectsBackground
import com.ucapdm2025.taskspaces.ui.components.projects.StatusVariations
import com.ucapdm2025.taskspaces.ui.components.projects.Tag
import com.ucapdm2025.taskspaces.ui.components.projects.Task
import com.ucapdm2025.taskspaces.ui.components.projects.TaskStatusColumn
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

/**
 * A composable function that displays the main Projects screen.
 *
 * This screen organizes tasks into horizontal columns based on their status:
 * Pending, Doing, and Done. Each column displays its corresponding tasks and
 * includes a button to add new tasks under that status.
 *
 * @param pendingTasks A list of tasks with [StatusVariations.PENDING] status.
 * @param doingTasks A list of tasks with [StatusVariations.DOING] status.
 * @param doneTasks A list of tasks with [StatusVariations.DONE] status.
 * @param onAddTaskClick A lambda function triggered when the "Add New Task" button is clicked in any column.
 * It receives the [StatusVariations] of the column where the button was pressed.
 */
@Composable
fun ProjectScreen(
    pendingTasks: List<Task>,
    doingTasks: List<Task>,
    doneTasks: List<Task>,
    onAddTaskClick: (StatusVariations) -> Unit
) {
    ProjectsBackground {
        LazyRow(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 28.dp)
        ) {
            item {
                TaskStatusColumn(
                    status = StatusVariations.PENDING,
                    tasks = pendingTasks,
                    onAddTaskClick = { onAddTaskClick(StatusVariations.PENDING) }
                )
            }
            item {
                TaskStatusColumn(
                    status = StatusVariations.DOING,
                    tasks = doingTasks,
                    onAddTaskClick = { onAddTaskClick(StatusVariations.DOING) }
                )
            }
            item {
                TaskStatusColumn(
                    status = StatusVariations.DONE,
                    tasks = doneTasks,
                    onAddTaskClick = { onAddTaskClick(StatusVariations.DONE) }
                )
            }
        }
    }
}

/**
 * A preview composable for the [ProjectsScreen] component.
 *
 * Displays a sample Projects screen with mock data for each task status,
 * allowing developers to visualize the layout and styling during design time.
 */
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProjectsScreenPreviewLight() {
    val pendingTasks = listOf(
        Task("Revisar requisitos", listOf(Tag("Análisis", Color(0xFF2E88DD)))),
        Task("Diseñar UI", listOf(Tag("UI", Color(0xFF2E88DD))))
    )

    val doingTasks = listOf(
        Task("Desarrollar login", listOf(Tag("Backend", Color(0xFFDD972E))))
    )

    val doneTasks = listOf(
        Task("Crear mockups", listOf(Tag("Diseño", Color(0xFF26AA5D))))
    )
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            ProjectScreen(
                pendingTasks = pendingTasks,
                doingTasks = doingTasks,
                doneTasks = doneTasks,
                onAddTaskClick = {}
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProjectsScreenPreviewDark() {
    val pendingTasks = listOf(
        Task("Revisar requisitos", listOf(Tag("Análisis", Color(0xFF2E88DD)))),
        Task("Diseñar UI", listOf(Tag("UI", Color(0xFF2E88DD))))
    )

    val doingTasks = listOf(
        Task("Desarrollar login", listOf(Tag("Backend", Color(0xFFDD972E))))
    )

    val doneTasks = listOf(
        Task("Crear mockups", listOf(Tag("Diseño", Color(0xFF26AA5D))))
    )
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            ProjectScreen(
                pendingTasks = pendingTasks,
                doingTasks = doingTasks,
                doneTasks = doneTasks,
                onAddTaskClick = {}
            )
        }
    }
}