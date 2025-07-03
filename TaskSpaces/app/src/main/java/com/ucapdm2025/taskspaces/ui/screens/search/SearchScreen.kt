package com.ucapdm2025.taskspaces.ui.screens.search


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ucapdm2025.taskspaces.data.model.ProjectModel
import com.ucapdm2025.taskspaces.data.model.TaskModel
import com.ucapdm2025.taskspaces.data.model.WorkspaceModel
import com.ucapdm2025.taskspaces.helpers.SearchHolder
import com.ucapdm2025.taskspaces.ui.components.general.FeedbackIcon
import com.ucapdm2025.taskspaces.ui.components.general.SearchContainer
import com.ucapdm2025.taskspaces.ui.components.home.WorkspaceCard
import com.ucapdm2025.taskspaces.ui.components.projects.TaskCard
import com.ucapdm2025.taskspaces.ui.components.workspace.ProjectCard

/**
 * Composable that displays a search screen with dynamic results based on a query.
 * It shows different UI states:
 * - Initial: when the search query is empty
 * - No Results: when there are no results for the query
 * - Results: when there is data for any category (workspaces, projects, tasks, users)
 */

@Composable
fun SearchScreen(
    onWorkspaceClick: (workspaceId: Int) -> Unit = { workspaceId -> },
    onProjectClick: (projectId: Int) -> Unit = { projectId -> },
    onTaskClick: (projectId: Int, taskId: Int) -> Unit = { projectId, taskId -> },
) {
    var searchResults = SearchHolder.results.value
    var searchQuery = SearchHolder.searchQuery.value
    var workspaces = searchResults?.workspaces ?: emptyList()
    var projects = searchResults?.projects ?: emptyList()
    var tasks = searchResults?.tasks ?: emptyList()

    var hasAnyResults =
        workspaces.isNotEmpty() || projects.isNotEmpty() || tasks.isNotEmpty()

    when {
        searchQuery.isEmpty() -> SearchInitialState()
        !hasAnyResults -> SearchNoResults()
        else -> SearchResults(
            workspaces,
            projects,
            tasks,
            onWorkspaceClick,
            onProjectClick,
            onTaskClick
        )
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
    workspaces: List<WorkspaceModel>,
    projects: List<ProjectModel>,
    tasks: List<TaskModel>,
    onWorkspaceClick: (workspaceId: Int) -> Unit = { workspaceId -> },
    onProjectClick: (projectId: Int) -> Unit = { projectId -> },
    onTaskClick: (projectId: Int, taskId: Int) -> Unit = { projectId, taskId -> },
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
                                name = it.title,
                                projectsCount = 2,
                                membersCount = 5,
                                onClick = { onWorkspaceClick(it.id) },
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
                            ProjectCard(name = it.title, onClick = { onProjectClick(it.id) })
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
                            TaskCard(
                                taskId = it.id,
                                breadcrumb = it.breadcrumb,
                                title = it.title,
                                tags = it.tags,
                                onClick = { onTaskClick(it.projectId, it.id) },
                                onDeleteClick = {}
                            )
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
    if (query == "zzz") emptyList() else listOf(
        "Project name",
        "Project name",
        "Project name",
        "Project name"
    )

fun sampleTasks(query: String): List<TaskModel> =
    if (query == "zzz") emptyList() else listOf(
        TaskModel(
            1, "TaskModel Title 1", projectId = 1,
//            listOf(Tag("Tag", Color.Red), Tag("Tag", Color.Green))
        ),
        TaskModel(
            2, "TaskModel Title 2", projectId = 1,
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
//@Preview(showBackground = true)
//@Composable
//fun SearchInitialPreview() {
//    TaskSpacesTheme {
//        ExtendedColors {
//            SearchScreen(searchQuery = "")
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun SearchWithResultsPreview() {
//    TaskSpacesTheme {
//        ExtendedColors {
//            SearchScreen(searchQuery = "example")
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun SearchNoResultsPreview() {
//    TaskSpacesTheme {
//        ExtendedColors {
//            SearchScreen(searchQuery = "zzz")
//        }
//    }
//}
//
//
//@Preview(showBackground = true, backgroundColor = 0xFF27272A )
//@Composable
//fun SearchInitialDarkPreview() {
//    TaskSpacesTheme(darkTheme = true) {
//        ExtendedColors(darkTheme = true) {
//            SearchScreen(searchQuery = "")
//        }
//    }
//}
//
//@Preview(showBackground = true, backgroundColor = 0xFF27272A)
//@Composable
//fun SearchWithResultsDarkPreview() {
//    TaskSpacesTheme(darkTheme = true) {
//        ExtendedColors(darkTheme = true) {
//            SearchScreen(searchQuery = "example")
//        }
//    }
//}
//
//@Preview(showBackground = true, backgroundColor = 0xFF27272A)
//@Composable
//fun SearchNoResultsDarkPreview() {
//    TaskSpacesTheme(darkTheme = true) {
//        ExtendedColors(darkTheme = true) {
//            SearchScreen(searchQuery = "zzz")
//        }
//    }
//}
