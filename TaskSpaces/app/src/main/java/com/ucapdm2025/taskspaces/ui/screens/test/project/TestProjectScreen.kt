package com.ucapdm2025.taskspaces.ui.screens.test.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucapdm2025.taskspaces.ui.components.general.Container
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

@Composable
fun TestProjectScreen(
    viewModel: TestProjectViewModel = viewModel()
) {
    val project = viewModel.project.collectAsStateWithLifecycle()
    val tasks = viewModel.tasks.collectAsStateWithLifecycle()

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

        Container(title = "- Workspace info", showOptionsButton = false) {
            Text(text = "ID: ${project.value?.id ?: "Loading..."}")
            Text(text = "Title: ${project.value?.title ?: "Loading..."}")
            Text(text = "Icon: ${project.value?.icon ?: "Loading..."}")
            Text(text = "Workspace ID: ${project.value?.workspaceId ?: "Loading..."}")
        }

        Container(title = "- Get all tasks by status: PENDING", showOptionsButton = false) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                val pendingTasks = tasks.value.filter { it.status == "PENDING" }

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

        Container(title = "- Get all tasks by status: DOING", showOptionsButton = false) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                val doingTasks = tasks.value.filter { it.status == "DOING" }

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

        Container(title = "- Get all tasks by status: DONE", showOptionsButton = false) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                val doneTasks = tasks.value.filter { it.status == "DONE" }

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