package com.ucapdm2025.taskspaces.ui.layout.topBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ucapdm2025.taskspaces.ui.components.SearchBar
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

/**
 * Top bar for the app, used in SearchScreen and BookmarksScreen.
 * @param query The current text value of the search bar.
 * @param onQueryChange A callback invoked when the text in the search bar changes.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBarWithSearchBar(query: String, onQueryChange: (String) -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
        ),
        title = { SearchBar(query = query, onQueryChange = onQueryChange) },
        navigationIcon = {
//            TODO: Show chevron only when stack is one route ahead of SearchRoute or BookmarksRoute
            IconButton(onClick = { /* TODO: Handle back navigation */ }) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun AppTopBarWithSearchBarLightPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            AppTopBarWithSearchBar(query = "Search", onQueryChange = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppTopBarWithSearchBarDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            AppTopBarWithSearchBar(query = "Search", onQueryChange = {})
        }
    }
}
