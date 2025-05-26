package com.ucapdm2025.taskspaces.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucapdm2025.taskspaces.data.model.Workspace
import com.ucapdm2025.taskspaces.ui.components.Container
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

// TODO: Implement the HomeScreen UI and styling
/**
 * A composable function that displays the home screen.
 * Currently, it shows a simple text message.
 *
 * @param modifier A [Modifier] for customizing the layout or behavior of the home screen.
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
) {
//    Consume viewModel states here
    val workspaces = viewModel.workspaces.collectAsStateWithLifecycle()
    val workspacesSharedWithMe = viewModel.workspacesSharedWithMe.collectAsStateWithLifecycle()
    val assignedTasks = viewModel.assignedTasks.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Container {
            Text(text = "Your workspaces")

//                Workspaces are being consumed here
            Column {
                workspaces.value.forEach { workspace ->
                    Card {
                        Text(
                            text = workspace.title
                        )
                    }
                }
            }

//            Create workspace action will go here
//            TODO: Implement a dialog to create a workspace
            Button(onClick = {}) {
                Text(text = "Create workspace")
            }

//            TODO: Add pagination loading here
            Button(onClick = {}) {
                Text(text = "See more")
            }
        }

        Container {
            Text(text = "Workspaces shared with me")

//                Workspaces shared with me are being consumed here
            Column {
                workspacesSharedWithMe.value.forEach { workspace ->
                    Card {
                        Text(text = workspace.title)
                    }
                }
            }

//            TODO: Add pagination loading here
            Button(onClick = {}) {
                Text(text = "See more")
            }
        }

//                Assigned tasks are being consumed here
        Container {
            Text(text = "Assigned tasks")

            Column {
                assignedTasks.value.forEach { task ->
                    Card {
                        Text(text = task.title)
                    }
                }
            }

//            TODO: Add pagination loading here
            Button(onClick = {}) {
                Text(text = "See more")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenLightPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            HomeScreen()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun HomeScreenDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            HomeScreen()
        }
    }
}