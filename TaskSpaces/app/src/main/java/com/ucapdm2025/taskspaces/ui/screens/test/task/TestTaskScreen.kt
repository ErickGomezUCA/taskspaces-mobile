package com.ucapdm2025.taskspaces.ui.screens.test.task

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
import com.ucapdm2025.taskspaces.ui.screens.task.TaskViewModel
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

private class MutableTask(
    var title: MutableState<String>,
    var description: MutableState<String>,
    var status: MutableState<String>,
    var projectId: MutableState<Int>
)

private class MutableComment(
    var content: MutableState<String>
)

@Composable
fun TestTaskScreen(
    viewModel: TaskViewModel = viewModel()
) {
    val task = viewModel.taskModel.collectAsStateWithLifecycle()
    val comments = viewModel.comments.collectAsStateWithLifecycle()

    val taskId = 1

    val selectedCommentId = remember {mutableStateOf("")}
    val mutableCommentInfo = remember { mutableStateOf(MutableComment(
        content = mutableStateOf("")
    )) }

    val mutableTaskInfo = remember {
        mutableStateOf(
            MutableTask(
                title = mutableStateOf(""),
                description = mutableStateOf(""),
                status = mutableStateOf("PENDING"), // Default status
                projectId = mutableIntStateOf(1) // Assuming projectId is 1 for this example
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
            text = "TestTaskViewModel example",
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        )

        Container(title = "- Task info", showOptionsButton = false) {
            Text(text = "ID: ${task.value?.id ?: "Loading..."}")
            Text(text = "Title: ${task.value?.title ?: "Loading..."}")
            Text(text = "Description: ${task.value?.description ?: "Loading..."}")
            Text(text = "Status: ${task.value?.status ?: "Loading..."}")
            Text(text = "Breadcrumb: ${task.value?.breadcrumb ?: "Loading..."}")
            Text(text = "ProjectID: ${task.value?.projectId ?: "Loading..."}")
        }

        Container(title = "- Update task", showOptionsButton = false) {
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
                    viewModel.updateTask(
                        id = taskId,
                        title = mutableTaskInfo.value.title.value,
                        description = mutableTaskInfo.value.description.value,
                        status = mutableTaskInfo.value.status.value,
                        projectId = mutableTaskInfo.value.projectId.value
                    )

                    mutableTaskInfo.value = MutableTask(
                        title = mutableStateOf(""),
                        description = mutableStateOf(""),
                        status = mutableStateOf(""),
                        projectId = mutableIntStateOf(1)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Update")
            }
        }

        Container(title = "- Get all Comments by Task ID", showOptionsButton = false) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                comments.value.forEach { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = "id: ${item.id}")
                        Text(text = "content: ${item.content}")
                        Text(text = "authorId: ${item.authorId}")
                    }
                }
            }
        }

        Container(title = "- Create Comment", showOptionsButton = false) {
            TextField(
                value = mutableCommentInfo.value.content.value,
                onValueChange = { mutableCommentInfo.value.content.value = it },
                placeholder = { Text(text = "Content") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.createComment(
                        content = mutableCommentInfo.value.content.value,
                    )

                    mutableCommentInfo.value = MutableComment(
                        content = mutableStateOf(""),
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Create")
            }
        }

        Container(title = "- Update Comment", showOptionsButton = false) {
            TextField(
                value = selectedCommentId.value,
                onValueChange = { selectedCommentId.value = it },
                placeholder = { Text(text = "ID") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = mutableCommentInfo.value.content.value,
                onValueChange = { mutableCommentInfo.value.content.value = it },
                placeholder = { Text(text = "Content") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.updateComment(
                        id = selectedCommentId.value.toInt(),
                        content = mutableCommentInfo.value.content.value,
                    )

                    selectedCommentId.value = ""

                    mutableCommentInfo.value = MutableComment(
                        content = mutableStateOf(""),
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Update")
            }
        }

        Container(title = "- Delete Comment", showOptionsButton = false) {
            TextField(
                value = selectedCommentId.value,
                onValueChange = { selectedCommentId.value = it },
                placeholder = { Text(text = "ID") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.deleteComment(
                        id = selectedCommentId.value.toInt(),
                    )

                    selectedCommentId.value = ""
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Delete")
            }
        }

        Container(title = "- Bookmark", showOptionsButton = false) {
            Button(
                onClick = {
                    viewModel.bookmarkTask()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Bookmark")
            }
        }
    }
}

/**
 * Preview function for the TestTaskScreen composable in light mode. This function allows you to see how the screen looks in light theme.
 */
@Preview(showBackground = true)
@Composable
fun TestTaskScreenLightPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            Scaffold { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding)) {
                    TestTaskScreen()
                }
            }
        }
    }
}

/**
 * Preview function for the TestTaskScreen composable in dark mode. This function allows you to see how the screen looks in dark theme.
 */
@Preview(showBackground = true)
@Composable
fun TestTaskScreenDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            Scaffold { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding)) {
                    TestTaskScreen()
                }
            }
        }
    }
}