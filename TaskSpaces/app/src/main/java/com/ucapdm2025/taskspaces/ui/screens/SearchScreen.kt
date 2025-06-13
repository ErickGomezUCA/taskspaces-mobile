package com.ucapdm2025.taskspaces.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ucapdm2025.taskspaces.ui.components.general.Container
import com.ucapdm2025.taskspaces.ui.components.general.FeedbackIcon
import com.ucapdm2025.taskspaces.ui.components.general.Tag
import com.ucapdm2025.taskspaces.ui.components.projects.Task
import com.ucapdm2025.taskspaces.ui.components.projects.TaskCard
import com.ucapdm2025.taskspaces.ui.components.workspace.ProjectCard
import com.ucapdm2025.taskspaces.ui.components.workspace.UserCard
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

//TODO: ADD COMMENTS
//TODO: MAKE NEW CONTAINER WITH HAVE VIEW MORE BUTTON INSTEAD OF THREE POINTS AND FIX THE DIMENSIONS OF THE CONTAINER AND PREVIEW
enum class SearchState {
    Default,
    WithResults,
    NoResults
}


@Composable
fun SearchScreen() {
    // Simulación de datos, puedes conectarlo a un ViewModel más adelante
    val projects = listOf("Project 1", "Project 2")
    val tasks = listOf(
        Task("Task 1", listOf(Tag("Urgent", Color.Red))),
        Task("Task 2", listOf(Tag("Low", Color.Green)))
    )
    val users = listOf("User1", "User2")

    val searchState = SearchState.WithResults // Cambia esto según sea necesario

    SearchScreenContent(
        projects = projects,
        tasks = tasks,
        users = users,
        searchState = searchState
    )
}

@Composable
fun SearchScreenContent(
    projects: List<String>,
    tasks: List<Task>,
    users: List<String>,
    searchState: SearchState,
    modifier: Modifier = Modifier
) {
    when (searchState) {
        SearchState.Default -> {
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                FeedbackIcon(
                    icon = Icons.Outlined.Close,
                    title = "There are no results with that term, try with another one"
                )
            }
        }

        SearchState.NoResults -> {
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                FeedbackIcon(
                    icon = Icons.Default.Search,
                    title = "No results found. Try a different keyword."
                )
            }
        }

        SearchState.WithResults -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Projects
                Container(title = "Projects") {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        projects.forEach { name ->
                            ProjectCard(name = name)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Tasks
                Container(title = "Tasks") {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        tasks.forEach { task ->
                            TaskCard(title = task.title, tags = task.tags)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Users
                Container(title = "Users") {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        users.forEach { username ->
                            UserCard(username = username)
                        }
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun SearchScreenPreview_Default() {
    TaskSpacesTheme {
        ExtendedColors {
            SearchScreenContent(
                projects = emptyList(),
                tasks = emptyList(),
                users = emptyList(),
                searchState = SearchState.Default
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview_NoResults() {
    TaskSpacesTheme {
        ExtendedColors {
            SearchScreenContent(
                projects = emptyList(),
                tasks = emptyList(),
                users = emptyList(),
                searchState = SearchState.NoResults
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview_WithResults() {
    val projects = listOf("Project 1", "Project 2")
    val tasks = listOf(
        Task("Task 1", listOf(Tag("Urgent", Color.Red))),
        Task("Task 2", listOf(Tag("Low", Color.Green)))
    )
    val users = listOf("User1", "User2")

    TaskSpacesTheme {
        ExtendedColors {
            SearchScreenContent(
                projects = projects,
                tasks = tasks,
                users = users,
                searchState = SearchState.WithResults
            )
        }
    }
}
