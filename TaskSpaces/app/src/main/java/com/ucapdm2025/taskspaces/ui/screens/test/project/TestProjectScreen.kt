package com.ucapdm2025.taskspaces.ui.screens.test.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucapdm2025.taskspaces.ui.components.general.Container
import com.ucapdm2025.taskspaces.ui.components.projects.StatusVariations
import com.ucapdm2025.taskspaces.ui.screens.project.ProjectViewModel
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

private class MutableTask(
    var title: MutableState<String>,
    var description: MutableState<String>,
    var status: MutableState<String>,
    var projectId: MutableState<Int>
)

@Composable
fun TestProjectScreen(
    viewModel: ProjectViewModel = viewModel()
) {
    val projectId = 1

    val project = viewModel.project.collectAsStateWithLifecycle()
    val tasks = viewModel.tasks.collectAsStateWithLifecycle()

    var selectedTaskId = remember { mutableStateOf("") }
    val mutableTaskInfo = remember {
        mutableStateOf(
            MutableTask(
                title = mutableStateOf(""),
                description = mutableStateOf(""),
                status = mutableStateOf("PENDING"), // Default status
                projectId = mutableIntStateOf(0) // Assuming projectId is 1 for this example
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "TestProjectViewModel example",
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        )

        Container(title = "- Project info") {
            Text(text = "ID: ${project.value?.id ?: "Loading..."}")
            Text(text = "Title: ${project.value?.title ?: "Loading..."}")
            Text(text = "Icon: ${project.value?.icon ?: "Loading..."}")
            Text(text = "Workspace ID: ${project.value?.workspaceId ?: "Loading..."}")
        }

        Container(title = "- Get all tasks by status: PENDING") {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                val pendingTasks = tasks.value.filter { it.status == StatusVariations.PENDING }

                pendingTasks.forEach { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = "id: ${item.id}")
                        Text(text = "title: ${item.title}")
                        Text(text = "breadcrumb: ${item.breadcrumb}")
                        Text(text = "deadline: ${item.deadline}")
                        Text(text = "status: ${item.status}")
                        Text(text = "projectId: ${item.projectId}")
                    }
                }
            }
        }

        Container(title = "- Get all tasks by status: DOING") {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                val doingTasks = tasks.value.filter { it.status == StatusVariations.DOING }

                doingTasks.forEach { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = "id: ${item.id}")
                        Text(text = "title: ${item.title}")
                        Text(text = "breadcrumb: ${item.breadcrumb}")
                        Text(text = "deadline: ${item.deadline}")
                        Text(text = "status: ${item.status}")
                        Text(text = "projectId: ${item.projectId}")
                    }
                }
            }
        }

        Container(title = "- Get all tasks by status: DONE") {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                val doneTasks = tasks.value.filter { it.status == StatusVariations.DONE }

                doneTasks.forEach { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = "id: ${item.id}")
                        Text(text = "title: ${item.title}")
                        Text(text = "breadcrumb: ${item.breadcrumb}")
                        Text(text = "deadline: ${item.deadline}")
                        Text(text = "status: ${item.status}")
                        Text(text = "projectId: ${item.projectId}")
                    }
                }
            }
        }

        Container(title = "- Create new task") {
            TextField(
                value = mutableTaskInfo.value.title.value,
                onValueChange = { mutableTaskInfo.value.title.value = it },
                placeholder = { Text(text = "Title") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = mutableTaskInfo.value.description.value,
                onValueChange = { mutableTaskInfo.value.description.value = it },
                placeholder = { Text(text = "Description") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = mutableTaskInfo.value.status.value,
                onValueChange = { mutableTaskInfo.value.status.value = it },
                placeholder = { Text(text = "Status") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.createTask(
                        title = mutableTaskInfo.value.title.value,
                        description = mutableTaskInfo.value.description.value,
                        status = when (mutableTaskInfo.value.status.value) {
                            "PENDING" -> StatusVariations.PENDING
                            "DOING" -> StatusVariations.DOING
                            "DONE" -> StatusVariations.DONE
                            else -> StatusVariations.PENDING
                        },
                        projectId = projectId
                    )

                    mutableTaskInfo.value = MutableTask(
                        title = mutableStateOf(""),
                        description = mutableStateOf(""),
                        status = mutableStateOf(""),
                        projectId = mutableIntStateOf(0)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Create")
            }
        }

        Container(title = "- Delete task") {
            TextField(
                value = selectedTaskId.value,
                onValueChange = { selectedTaskId.value = it },
                placeholder = { Text(text = "ID") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.deleteTask(selectedTaskId.value.toInt())

                    selectedTaskId.value = ""
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Delete")
            }
        }

    }
}

/**
 * Preview function for the TestProjectScreen composable in light mode. This function allows you to see how the screen looks in light theme.
 */
@Preview(showBackground = true)
@Composable
fun TestProjectScreenLightPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            Scaffold { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding)) {
                    TestProjectScreen()
                }
            }
        }
    }
}

/**
 * Preview function for the TestProjectScreen composable in dark mode. This function allows you to see how the screen looks in dark theme.
 */
@Preview(showBackground = true)
@Composable
fun TestProjectScreenDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            Scaffold { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding)) {
                    TestProjectScreen()
                }
            }
        }
    }
}