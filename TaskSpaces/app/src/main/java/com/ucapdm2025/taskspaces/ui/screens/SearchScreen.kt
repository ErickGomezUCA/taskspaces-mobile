package com.ucapdm2025.taskspaces.ui.screens


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
import com.ucapdm2025.taskspaces.data.model.TaskModel
import com.ucapdm2025.taskspaces.ui.components.general.*
import com.ucapdm2025.taskspaces.ui.components.home.WorkspaceCard
import com.ucapdm2025.taskspaces.ui.components.projects.TaskCard
import com.ucapdm2025.taskspaces.ui.components.workspace.ProjectCard
import com.ucapdm2025.taskspaces.ui.components.workspace.UserCard
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

/**
 * Composable that displays a search screen with dynamic results based on a query.
 * It shows different UI states:
 * - Initial: when the search query is empty
 * - No Results: when there are no results for the query
 * - Results: when there is data for any category (workspaces, projects, tasks, users)
 *
 * @param searchQuery  The search input from the user.
 * @param workspaces  List of workspace to show.
 * @param projects List of project to show.
 * @param tasks List of tasks with tags.
 * @param users List of users.
 */

@Composable
fun SearchScreen(
    searchQuery: String = "", //switch to see "" default , "example" with results , "zzz" without results
    workspaces: List<String> = sampleWorkspaces(searchQuery),
    projects: List<String> = sampleProjects(searchQuery),
    tasks: List<TaskModel> = sampleTasks(searchQuery),
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

/**
 * UI shown when the user hasn't typed anything in the search field.
 */
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

/**
 * UI shown when there are no results for the current search query.
 */
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

/**
 * UI for displaying search results grouped by category:
 * - Workspaces, Projects, Tasks, Users
 *
 * Each section is scrollable horizontally inside a vertical scroll.
 */
@Composable
fun SearchResults(
    workspaces: List<String>,
    projects: List<String>,
    tasks: List<TaskModel>,
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
                    LazyRow( horizontalArrangement = Arrangement.spacedBy(18.dp)) {
                        items(workspaces) {
                            WorkspaceCard(
                                name = it,
                                projectsCount = 2,
                                membersCount = 5,
                                modifier = Modifier
                                    .widthIn(min = 200.dp, max = 300.dp)
                                    .padding(end = 8.dp)
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
//                            TaskCard(title = it.title, tags = it.tags)
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

// Simulated data for preview
fun sampleWorkspaces(query: String): List<String> =
    if (query == "zzz") emptyList() else listOf("Workspace 1", "Workspace 2")

fun sampleProjects(query: String): List<String> =
    if (query == "zzz") emptyList() else listOf("Project name", "Project name","Project name", "Project name")

fun sampleTasks(query: String): List<TaskModel> =
    if (query == "zzz") emptyList() else listOf(
        TaskModel(1, "TaskModel Title 1", projectId = 1,
//            listOf(Tag("Tag", Color.Red), Tag("Tag", Color.Green))
        ),
        TaskModel(2, "TaskModel Title 2", projectId = 1,
//            listOf(Tag("Tag", Color.Blue))
        )
    )

fun sampleUsers(query: String): List<String> =
    if (query == "zzz") emptyList() else listOf("Username", "Username", "Username", "Username")


/**
 * Previews for the [SearchScreen] composable in various UI states:
 * - Initial, With results, No results
 * - Light and dark theme variants
 */
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
