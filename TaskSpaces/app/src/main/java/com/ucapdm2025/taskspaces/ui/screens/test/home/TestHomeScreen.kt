package com.ucapdm2025.taskspaces.ui.screens.test.home

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
import com.ucapdm2025.taskspaces.ui.screens.test.users.TestUserScreen
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

data class MutableWorkspace(
    var title: MutableState<String>,
    var ownerId: MutableState<Int>
)

@Composable
fun TestHomeScreen(
    viewModel: TestHomeViewModel = viewModel()
) {
//    Variables and states
    val workspaces = viewModel.workspaces.collectAsStateWithLifecycle()
    val workspacesSharedWithMe = viewModel.workspacesSharedWithMe.collectAsStateWithLifecycle()
    val assignedTasks = viewModel.assignedTasks.collectAsStateWithLifecycle()

//    Other states:
//    1. IDs
    val selectedWorkspaceId = remember { mutableStateOf("") }
//    2. Create and update workspace
    val mutableWorkspaceInfo: MutableState<MutableWorkspace> = remember {
        mutableStateOf(
            MutableWorkspace(
                title = mutableStateOf(""),
                ownerId = mutableIntStateOf(0)
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
        Text(text = "TestHomeViewModel example", fontWeight = FontWeight.Medium, fontSize = 20.sp)

        Container(title = "- Get workspaces from user Id", showOptionsButton = false) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                workspaces.value.forEach { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = "id: ${item?.id}")
                        Text(text = "title: ${item?.title}")
                        Text(text = "ownerId: ${item?.ownerId}")
                    }
                }
            }
        }

        Container(title = "- Create new workspace", showOptionsButton = false) {
            TextField(
                value = mutableWorkspaceInfo.value.title.value,
                onValueChange = { mutableWorkspaceInfo.value.title.value = it },
                placeholder = { Text(text = "Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.createWorkspace(mutableWorkspaceInfo.value.title.value)

                    mutableWorkspaceInfo.value = MutableWorkspace(
                        title = mutableStateOf(""),
                        ownerId = mutableIntStateOf(0)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Create")
            }
        }

        Container(title = "- Update workspace", showOptionsButton = false) {
            TextField(
                value = selectedWorkspaceId.value,
                onValueChange = { selectedWorkspaceId.value = it },
                placeholder = { Text(text = "ID") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = mutableWorkspaceInfo.value.title.value,
                onValueChange = { mutableWorkspaceInfo.value.title.value = it },
                placeholder = { Text(text = "Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.updateWorkspace(
                        selectedWorkspaceId.value.toInt(),
                        mutableWorkspaceInfo.value.title.value
                    )

                    selectedWorkspaceId.value = ""

                    mutableWorkspaceInfo.value = MutableWorkspace(
                        title = mutableStateOf(""),
                        ownerId = mutableIntStateOf(0)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Update")
            }
        }

        Container(title = "- Delete workspace", showOptionsButton = false) {
            TextField(
                value = selectedWorkspaceId.value,
                onValueChange = { selectedWorkspaceId.value = it },
                placeholder = { Text(text = "ID") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.deleteWorkspace(selectedWorkspaceId.value.toInt())

                    selectedWorkspaceId.value = ""
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Delete")
            }
        }

        Container(title = "- Get shared workspaces", showOptionsButton = false) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                workspacesSharedWithMe.value.forEach { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = "id: ${item?.id}")
                        Text(text = "title: ${item?.title}")
                        Text(text = "ownerId: ${item?.ownerId}")
                    }
                }
            }
        }

        Container(title = "- Get assigned tasks", showOptionsButton = false) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                assignedTasks.value.forEach { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = "id: ${item?.id}")
                        Text(text = "title: ${item?.title}")
                    }
                }
            }
        }
    }
}

/**
 * Preview function for the TestHomeScreen composable in light mode. This function allows you to see how the screen looks in light theme.
 */
@Preview(showBackground = true)
@Composable
fun TestHomeScreenLightPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            Scaffold { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding)) {
                    TestHomeScreen()
                }
            }
        }
    }
}

/**
 * Preview function for the TestHomeScreen composable in dark mode. This function allows you to see how the screen looks in dark theme.
 */
@Preview(showBackground = true)
@Composable
fun TestHomeScreenDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            Scaffold { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding)) {
                    TestHomeScreen()
                }
            }
        }
    }
}