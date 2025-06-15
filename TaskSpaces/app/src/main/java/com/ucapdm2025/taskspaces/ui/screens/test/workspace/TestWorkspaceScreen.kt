package com.ucapdm2025.taskspaces.ui.screens.test.workspace

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucapdm2025.taskspaces.TaskSpacesApplication
import com.ucapdm2025.taskspaces.ui.components.general.Container
import com.ucapdm2025.taskspaces.ui.screens.workspace.WorkspaceViewModel
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

private data class MutableProject(
    var title: MutableState<String>,
    var icon: MutableState<String>,
    var workspaceId: MutableState<Int>
)

private data class MutableMember(
    var username: MutableState<String>,
    var memberRole: MutableState<String>
)

@Composable
fun TestWorkspaceScreen(
    viewModel: WorkspaceViewModel = viewModel(factory = WorkspaceViewModel.Factory)
) {
    val workspaceId = 1;

    val workspace = viewModel.workspace.collectAsStateWithLifecycle()
    val projects = viewModel.projects.collectAsStateWithLifecycle()
    val members = viewModel.members.collectAsStateWithLifecycle()

    val selectedProjectId = remember { mutableStateOf("") }

    val mutableProjectInfo: MutableState<MutableProject> = remember {
        mutableStateOf(
            MutableProject(
                title = mutableStateOf(""),
                icon = mutableStateOf(""),
                workspaceId = mutableIntStateOf(0)
            )
        )
    }

    val mutableMemberInfo: MutableState<MutableMember> = remember {
        mutableStateOf(
            MutableMember(
                username = mutableStateOf(""),
                memberRole = mutableStateOf("")
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
            text = "TestWorkspaceViewModel example",
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        )

        Container(title = "- Workspace info") {
            Text(text = "ID: ${workspace.value?.id ?: "Loading..."}")
            Text(text = "Title: ${workspace.value?.title ?: "Loading..."}")
            Text(text = "Owner ID: ${workspace.value?.ownerId ?: "Loading..."}")
        }

        Container(title = "- Get all projects by workspace id") {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                projects.value.forEach { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = "id: ${item.id}")
                        Text(text = "title: ${item.title}")
                        Text(text = "workspaceId: ${item.workspaceId}")
                    }
                }
            }
        }

        Container(title = "- Create new project") {
            TextField(
                value = mutableProjectInfo.value.title.value,
                onValueChange = { mutableProjectInfo.value.title.value = it },
                placeholder = { Text(text = "Title") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = mutableProjectInfo.value.icon.value,
                onValueChange = { mutableProjectInfo.value.icon.value = it },
                placeholder = { Text(text = "Icon") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.createProject(
                        title = mutableProjectInfo.value.title.value,
                        icon = mutableProjectInfo.value.icon.value,
                        workspaceId = workspaceId
                    )

                    mutableProjectInfo.value = MutableProject(
                        title = mutableStateOf(""),
                        icon = mutableStateOf(""),
                        workspaceId = mutableIntStateOf(0)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Create")
            }
        }

        Container(title = "- Update project") {
            TextField(
                value = selectedProjectId.value,
                onValueChange = { selectedProjectId.value = it },
                placeholder = { Text(text = "ID") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = mutableProjectInfo.value.title.value,
                onValueChange = { mutableProjectInfo.value.title.value = it },
                placeholder = { Text(text = "Title") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = mutableProjectInfo.value.icon.value,
                onValueChange = { mutableProjectInfo.value.icon.value = it },
                placeholder = { Text(text = "Icon") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.updateProject(
                        id = selectedProjectId.value.toInt(),
                        title = mutableProjectInfo.value.title.value,
                        icon = mutableProjectInfo.value.icon.value,
                        workspaceId = workspaceId
                    )

                    selectedProjectId.value = ""

                    mutableProjectInfo.value = MutableProject(
                        title = mutableStateOf(""),
                        icon = mutableStateOf(""),
                        workspaceId = mutableIntStateOf(0)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Update")
            }
        }

        Container(title = "- Delete workspace") {
            TextField(
                value = selectedProjectId.value,
                onValueChange = { selectedProjectId.value = it },
                placeholder = { Text(text = "ID") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.deleteProject(selectedProjectId.value.toInt())

                    selectedProjectId.value = ""
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Delete")
            }
        }

        Container(title = "- Get all members") {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                members.value.forEach { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = "id: ${item.id}")
                        Text(text = "username: ${item.username}")
                    }
                }
            }
        }

        Container(title = "- Add member") {
            TextField(
                value = mutableMemberInfo.value.username.value,
                onValueChange = { mutableMemberInfo.value.username.value = it },
                placeholder = { Text(text = "Username") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = mutableMemberInfo.value.memberRole.value,
                onValueChange = { mutableMemberInfo.value.memberRole.value = it },
                placeholder = { Text(text = "Member role") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.addMember(
                        username = mutableMemberInfo.value.username.value,
                        memberRole = mutableMemberInfo.value.memberRole.value,
                        workspaceId = workspaceId
                    )

                    mutableMemberInfo.value = MutableMember(
                        username = mutableStateOf(""),
                        memberRole = mutableStateOf("")
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Create")
            }

        }

        Container(title = "- Remove member") {
            TextField(
                value = mutableMemberInfo.value.username.value,
                onValueChange = { mutableMemberInfo.value.username.value = it },
                placeholder = { Text(text = "Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.removeMember(
                        username = mutableMemberInfo.value.username.value,
                        workspaceId = workspaceId
                    )

                    mutableMemberInfo.value = MutableMember(
                        username = mutableStateOf(""),
                        memberRole = mutableStateOf("")
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Create")
            }

        }

    }
}

/**
 * Preview function for the TestWorkspaceScreen composable in light mode. This function allows you to see how the screen looks in light theme.
 */
@Preview(showBackground = true)
@Composable
fun TestWorkspaceScreenLightPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            Scaffold { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding)) {
                    TestWorkspaceScreen()
                }
            }
        }
    }
}

/**
 * Preview function for the TestWorkspaceScreen composable in dark mode. This function allows you to see how the screen looks in dark theme.
 */
@Preview(showBackground = true)
@Composable
fun TestWorkspaceScreenDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            Scaffold { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding)) {
                    TestWorkspaceScreen()
                }
            }
        }
    }
}