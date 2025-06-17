package com.ucapdm2025.taskspaces.ui.screens.project

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucapdm2025.taskspaces.TaskSpacesApplication
import com.ucapdm2025.taskspaces.data.model.TagModel
import com.ucapdm2025.taskspaces.data.model.TaskModel
import com.ucapdm2025.taskspaces.ui.components.general.FeedbackIcon
import com.ucapdm2025.taskspaces.ui.components.projects.ProjectsBackground
import com.ucapdm2025.taskspaces.ui.components.projects.StatusVariations
import com.ucapdm2025.taskspaces.ui.components.projects.TaskStatusColumn
import com.ucapdm2025.taskspaces.ui.components.task.TaskDialog
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

/**
 * A composable function that displays the main Projects screen.
 *
 * This screen organizes tasks into horizontal columns based on their status:
 * Pending, Doing, and Done. Each column displays its corresponding tasks and
 * includes a button to add new tasks under that status.
 *
 * @param projectId The ID of the project to display tasks for.
 */
@Composable
fun ProjectScreen(
    projectId: Int,
    taskId: Int? = null
) {
    val application = LocalContext.current.applicationContext as TaskSpacesApplication
    val projectRepository = application.appProvider.provideProjectRepository()
    val taskRepository = application.appProvider.provideTaskRepository()
    val viewModel: ProjectViewModel = viewModel(factory = ProjectViewModelFactory(projectId, projectRepository, taskRepository))

    val project = viewModel.project.collectAsStateWithLifecycle()
    val tasks = viewModel.tasks.collectAsStateWithLifecycle()
    val showTaskDialog = viewModel.showTaskDialog.collectAsStateWithLifecycle()
    val selectedTaskId = viewModel.selectedTaskId.collectAsStateWithLifecycle()

    val pendingTasks = tasks.value.filter { it.status == StatusVariations.PENDING }
    val doingTasks = tasks.value.filter { it.status == StatusVariations.DOING }
    val doneTasks = tasks.value.filter { it.status == StatusVariations.DONE }

    fun onTaskCardClick(taskId: Int) {
        viewModel.setSelectedTaskId(taskId)
        viewModel.showTaskDialog()
    }

//    Automatically open task dialog of the specified taskId if provided.
//    Do this only on first load of project screen.
    LaunchedEffect(taskId) {
        if (taskId != null) {
            viewModel.setSelectedTaskId(taskId)
            viewModel.showTaskDialog()
        }
    }
    
//    Show feedback icon if the project is not found
    if (project.value == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            FeedbackIcon(
                icon = Icons.Default.Close,
                title = "Sorry, we couldn't find this project.",
            )
            return
        }
    }

//    Task Dialog
    if (showTaskDialog.value) {
        TaskDialog(
            taskId = selectedTaskId.value,
            onDismissRequest = { viewModel.hideTaskDialog() },
        )
    }

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
                    onTaskCardClick = { taskId ->
                        onTaskCardClick(taskId)
                    },
                    onAddTaskClick = {
                        viewModel.createTask(
                            title = "New task",
                            status = StatusVariations.PENDING,
                        )
                        viewModel.showTaskDialog()
                    }
                )
            }
            item {
                TaskStatusColumn(
                    status = StatusVariations.DOING,
                    tasks = doingTasks,
                    onTaskCardClick = { taskId ->
                        onTaskCardClick(taskId)
                    },
                    onAddTaskClick = {
                        viewModel.createTask(
                            title = "New task",
                            status = StatusVariations.DOING,
                        )
                        viewModel.showTaskDialog()
                    }
                )
            }
            item {
                TaskStatusColumn(
                    status = StatusVariations.DONE,
                    tasks = doneTasks,
                    onTaskCardClick = { taskId ->
                        onTaskCardClick(taskId)
                    },
                    onAddTaskClick = {
                        viewModel.createTask(
                            title = "New task",
                            status = StatusVariations.DONE,
                        )
                        viewModel.showTaskDialog()
                    }
                )
            }
        }
    }
}

/**
 * A preview composable for the [ProjectScreen] component.
 *
 * Displays a sample Projects screen with mock data for each task status,
 * allowing developers to visualize the layout and styling during design time.
 */
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProjectsScreenPreviewLight() {
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            ProjectScreen(
                projectId = 1,
            )
        }
    }
}

/**
 * A preview composable for the [ProjectScreen] component using the dark theme.
 *
 * Displays a sample Projects screen with mock tasks for each status category
 * (Pending, Doing, Done) to visualize the layout and styling in dark mode.
 */
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProjectsScreenPreviewDark() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            ProjectScreen(
                projectId = 1,
            )
        }
    }
}