package com.ucapdm2025.taskspaces.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ucapdm2025.taskspaces.ui.components.general.*
import com.ucapdm2025.taskspaces.ui.components.home.WorkspaceCard
import com.ucapdm2025.taskspaces.ui.components.projects.Task
import com.ucapdm2025.taskspaces.ui.components.projects.TaskCard
import com.ucapdm2025.taskspaces.ui.components.workspace.ProjectCard
import com.ucapdm2025.taskspaces.ui.components.workspace.UserCard
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme
// TODO: fix a bug workspace
// TODO: add comments,fix dimensions and add preview
@Composable
fun SearchScreen(
    searchQuery: String = "",
    workspaces: List<String> = sampleWorkspaces(searchQuery),
    projects: List<String> = sampleProjects(searchQuery),
    tasks: List<Task> = sampleTasks(searchQuery),
    users: List<String> = sampleUsers(searchQuery),
) {
    val hasAnyResults =
        workspaces.isNotEmpty() || projects.isNotEmpty() || tasks.isNotEmpty() || users.isNotEmpty()

    when {
        searchQuery.isEmpty() -> SearchInitialState()
        !hasAnyResults -> SearchNoResults()
        else -> SearchResults(workspaces, projects, tasks, users)
    }
}

@Composable
fun SearchInitialState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        FeedbackIcon(
            icon = Icons.Default.Search,
            title = "Start searching workspaces, projects, tasks and users"
        )
    }
}

@Composable
fun SearchNoResults() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        FeedbackIcon(
            icon = Icons.Outlined.Close,
            title = "There are no results with that term, try with another one"

        )
    }
}

@Composable
fun SearchResults(
    workspaces: List<String>,
    projects: List<String>,
    tasks: List<Task>,
    users: List<String>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        if (workspaces.isNotEmpty()) {
            item {
                SearchContainer(title = "Workspaces", onViewMoreClick = { }) {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
                        items(workspaces) {
                            WorkspaceCard(
                                name = it,
                                projectsCount = 2,
                                membersCount = 5
                            )
                        }
                    }
                }
            }
        }

        if (projects.isNotEmpty()) {
            item {
                SearchContainer(title = "Projects", onViewMoreClick = { }) {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(projects) {
                            ProjectCard(name = it)
                        }
                    }
                }
            }
        }

        if (tasks.isNotEmpty()) {
            item {
                SearchContainer(title = "Tasks", onViewMoreClick = { }) {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(tasks) {
                            TaskCard(title = it.title, tags = it.tags)
                        }
                    }
                }
            }
        }

        if (users.isNotEmpty()) {
            item {
                SearchContainer(title = "Users", onViewMoreClick = { }) {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(users) {
                            UserCard(username = it)
                        }
                    }
                }
            }
        }
    }
}

// Simulated data for preview/testing
fun sampleWorkspaces(query: String): List<String> =
    if (query == "zzz") emptyList() else listOf("Workspace 1", "Workspace 2")

fun sampleProjects(query: String): List<String> =
    if (query == "zzz") emptyList() else listOf("Project name", "Project name","Project name", "Project name")

fun sampleTasks(query: String): List<Task> =
    if (query == "zzz") emptyList() else listOf(
        Task("Task Title 1", listOf(Tag("Tag", Color.Red), Tag("Tag", Color.Green))),
        Task("Task Title 2", listOf(Tag("Tag", Color.Blue)))
    )

fun sampleUsers(query: String): List<String> =
    if (query == "zzz") emptyList() else listOf("Username", "Username", "Username", "Username")

// Previews
@Preview(showBackground = true)
@Composable
fun SearchInitialPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            SearchScreen(searchQuery = "")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchWithResultsPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            SearchScreen(searchQuery = "example")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchNoResultsPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            SearchScreen(searchQuery = "zzz")
        }
    }
}

// Previews
@Preview(showBackground = true, backgroundColor = 0xFF27272A )
@Composable
fun SearchInitialDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            SearchScreen(searchQuery = "")
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun SearchWithResultsDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            SearchScreen(searchQuery = "example")
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun SearchNoResultsDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            SearchScreen(searchQuery = "zzz")
        }
    }
}
