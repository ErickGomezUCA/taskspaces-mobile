package com.ucapdm2025.taskspaces.ui.screens.search

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucapdm2025.taskspaces.ui.components.general.Container
import com.ucapdm2025.taskspaces.ui.components.general.FeedbackIcon
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

// TODO: Implement the ScreenScreen UI
/**
 * A composable function that displays the search screen.
 * Currently, it shows a feedback icon with a message.
 *
 * @param modifier A [Modifier] for customizing the layout or behavior of the search screen.
 */
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel(),
) {
    val results = viewModel.results.collectAsStateWithLifecycle()

//    TODO: Replace this method into a proper query handler
    val query = remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        if (results.value.isEmpty()) {
            if (query.value == "") {
                Button(onClick = {
                    query.value = "hi"
                    viewModel.searchResults(query.value)
                }) { Text(text = "Get results") }
                    FeedbackIcon(
                        icon = Icons.Default.Search,
                        title = "Start searching workspaces, projects, tasks and users"
                    )
            } else {
                    FeedbackIcon(
                        icon = Icons.Default.Close,
                        title = "There are no results with that term, try with another one"
                    )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                Container {
                    Text(text = "Workspaces")

                    Row {
                        results.value.workspaces?.forEach { workspace ->
                            Card {
                                Text(text = workspace.title)
                            }
                        }
                    }
                }

                Container {
                    Text(text = "Projects")

                    Row {
                        results.value.projects?.forEach { project ->
                            Card {
                                Text(text = project.title)
                            }
                        }
                    }
                }

                Container {
                    Text(text = "Tasks")

                    Row {
                        results.value.tasks?.forEach { task ->
                            Card {
                                Text(text = task.title)
                            }
                        }
                    }
                }

                Container {
                    Text(text = "Users")

                    Row {
                        results.value.users?.forEach { user ->
                            Card {
                                Text(text = user.username)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenLightPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            SearchScreen()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun SearchScreenDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            SearchScreen()
        }
    }
}