package com.ucapdm2025.taskspaces.ui.screens.bookmarks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

// TODO: Implement the BookmarksScreen UI
/**
 * A composable function that displays the bookmarks screen.
 * Currently, it shows a feedback icon with a message.
 *
 * @param modifier A [Modifier] for customizing the layout or behavior of the bookmarks screen.
 */
@Composable
fun BookmarksScreen(
    modifier: Modifier = Modifier,
    viewModel: BookmarksViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
// TODO: Later show this feedback icon when there are no bookmarks
        //        FeedbackIcon(
//            icon = Icons.Default.BookmarkBorder,
//            title = "Navigate through your tasks easily with your bookmarks"
//        )

        val bookmarkedTasks = viewModel.bookmarkedTasks.collectAsStateWithLifecycle()

        Text(text = "Sort by: ")

        Container {
            Column(modifier = Modifier.fillMaxSize()) {
                bookmarkedTasks.value.forEach { task ->
                    Card {
                        Text(
                            text = task.title
                        )
                    }
                }
            }
        }
    }
}

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