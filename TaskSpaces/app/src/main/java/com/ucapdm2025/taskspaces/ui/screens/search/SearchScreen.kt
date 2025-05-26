package com.ucapdm2025.taskspaces.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucapdm2025.taskspaces.ui.components.FeedbackIcon
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
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        FeedbackIcon(
            icon = Icons.Default.Search,
            title = "Start searching workspaces, projects, tasks and users"
        )
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