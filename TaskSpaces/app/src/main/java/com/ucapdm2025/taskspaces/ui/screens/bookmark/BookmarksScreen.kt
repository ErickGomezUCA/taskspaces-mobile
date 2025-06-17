package com.ucapdm2025.taskspaces.ui.screens.bookmark

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucapdm2025.taskspaces.TaskSpacesApplication
import com.ucapdm2025.taskspaces.data.model.TagModel
import com.ucapdm2025.taskspaces.data.model.TaskModel
import com.ucapdm2025.taskspaces.ui.components.general.Container
import com.ucapdm2025.taskspaces.ui.components.general.FeedbackIcon
import com.ucapdm2025.taskspaces.ui.components.general.SortBySelector
import com.ucapdm2025.taskspaces.ui.components.general.SortOption
import com.ucapdm2025.taskspaces.ui.components.projects.TaskCard
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme
import kotlin.collections.component1
import kotlin.collections.component2

// TODO: Connect BookmarksScreen to real ViewModel state.

/**
 * Displays the bookmarks screen with one of three states:
 * - Empty if no bookmarks exist
 * - No results if the search query returns nothing
 * - A list of bookmarked tasks otherwise
 *
 * @param bookmarkedTasks The list of bookmarked tasks
 * @param searchQuery The current search filter applied to task titles
 */
@Composable
fun BookmarksScreen(
    searchQuery: String = ""
) { //change to show no results
    val application = LocalContext.current.applicationContext as TaskSpacesApplication
    val bookmarkRepository = application.appProvider.provideBookmarkRepository()
    val viewModel: BookmarkViewModel = viewModel(factory = BookmarkViewModelFactory(bookmarkRepository))

    val bookmarkedTasks = viewModel.bookmarkedTasks.collectAsStateWithLifecycle()

    val filtered = bookmarkedTasks.value.filter {
        it.title.contains(searchQuery, ignoreCase = true)
    }
    when {
        bookmarkedTasks.value.isEmpty() -> {
            EmptyBookmarks()
        }

        filtered.isEmpty() -> {
            NoResults()
        }

        else -> {
            BookmarkList(filtered)
        }
    }
    // TODO: Replace sampleTasks() with actual ViewModel-backed state
    // TODO: Hook into navigation when a task is clicked (from TaskCard)
}

/**
 * Displays a centered icon and message indicating that no bookmarks have been saved.
 */
@Composable
fun EmptyBookmarks() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        FeedbackIcon(
            icon = Icons.Default.BookmarkBorder,
            title = "Navigate through your tasks easily with your bookmarks"
        )
    }
}

/**
 * Displays a centered icon and message indicating that no bookmarks have been saved.
 */
@Composable
fun NoResults() {
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
 * Displays a list of bookmarked tasks inside a styled container.
 *
 * @param bookmarks List of tasks to display as cards
 */
@Composable
fun BookmarkList(bookmarks: List<TaskModel>) {
    var sortOption by remember { mutableStateOf(SortOption.NAME) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        SortBySelector(
            selected = sortOption,
            onSelect = { sortOption = it }
        )

        val groupedTasks: Map<String, List<TaskModel>> = bookmarks.groupBy { it.breadcrumb }
        // TODO: Apply real sorting logic later based on sortOption

//        Show bookmarked tasks grouped by breadcrumb
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            groupedTasks.forEach { (breadcrumb, tasksInGroup) ->
                Container(title = breadcrumb) {
                    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        tasksInGroup.forEach { task ->
                            TaskCard(
                                title = task.title,
//                                TODO: Add tags
                                tags = emptyList() // or task.tags if available
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Preview of the bookmarks screen with content.
 * Shows a list of tasks with no active search filter.
 */
@Preview(showBackground = true)
@Composable
fun BookmarksScreenLightPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            BookmarksScreen()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun BookmarksScreenDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            BookmarksScreen()
        }
    }
}

/**
 * Preview of the bookmarks screen in "No Results" state.
 * Shows empty results due to unmatched search query.
 */
@Preview(showBackground = true)
@Composable
fun BookmarksNoResultsPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            BookmarksScreen(
                searchQuery = "ZZZ" //empty search example
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun BookmarksNoResultsDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            BookmarksScreen(
                searchQuery = "ZZZ"  //empty search example
            )
        }
    }
}


/**
 * Preview of the bookmarks screen in "Empty" state.
 * No bookmarks are present in the list.
 */
@Preview(showBackground = true)
@Composable
fun BookmarksEmptyPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            BookmarksScreen(
                searchQuery = ""
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun BookmarksEmptyDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            BookmarksScreen(
                searchQuery = ""
            )
        }
    }
}



