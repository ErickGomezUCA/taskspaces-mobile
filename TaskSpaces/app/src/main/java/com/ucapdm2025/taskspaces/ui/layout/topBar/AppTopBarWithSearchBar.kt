package com.ucapdm2025.taskspaces.ui.layout.topBar

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ucapdm2025.taskspaces.ui.components.general.SearchBar
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

/**
 * Top bar for the app, used in all screens.
 * @param query The current text value of the search bar.
 * @param placeholder The placeholder text displayed when the search bar is empty.
 * @param onQueryChange A callback invoked when the text in the search bar changes.
 * @param onSearch A callback invoked when the user submits a search action.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBarWithSearchBar(
    query: String,
    placeholder: String = "Placeholder...",
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
        ),
        title = {
            SearchBar(
                query = query,
                placeholder = placeholder,
                onQueryChange = onQueryChange,
                onSearch = onSearch,
                modifier = Modifier.padding(end = 14.dp)
            )
        },
        navigationIcon = {
//            TODO: Show chevron only when stack is one route ahead of SearchRoute or BookmarksRoute
//            IconButton(onClick = { /* TODO: Handle back navigation */ }) {
//                Icon(
//                    imageVector = Icons.Default.ArrowBackIosNew,
//                    contentDescription = "Back",
//                    tint = MaterialTheme.colorScheme.onBackground
//                )
//            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun AppTopBarWithSearchBarLightPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            AppTopBarWithSearchBar(
                query = "",
                placeholder = "Search...",
                onQueryChange = {},
                onSearch = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppTopBarWithSearchBarWithValueLightPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            AppTopBarWithSearchBar(
                query = "Query value",
                placeholder = "Search...",
                onQueryChange = {},
                onSearch = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppTopBarWithSearchBarDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            AppTopBarWithSearchBar(
                query = "",
                placeholder = "Search tasks...",
                onQueryChange = {},
                onSearch = {})
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppTopBarWithSearchBarWithValueDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            AppTopBarWithSearchBar(
                query = "Query value",
                placeholder = "Search tasks...",
                onQueryChange = {},
                onSearch = {})
        }
    }
}
