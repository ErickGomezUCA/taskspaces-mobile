package com.ucapdm2025.taskspaces.ui.components.general

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

/**
 * A composable function that displays a search bar with customizable behavior and appearance.
 *
 * @param query The current text value of the search bar.
 * @param modifier A [Modifier] for customizing the layout or behavior of the search bar.
 * @param placeholder The placeholder text displayed when the search bar is empty. Defaults to "Placeholder...".
 * @param onQueryChange A callback invoked when the text in the search bar changes.
 * @param onSearch A callback invoked when the user submits a search action. Defaults to an empty lambda.
 * @param enabled A boolean indicating whether the search bar is enabled. Defaults to `true`.
 */
@Composable
fun SearchBar(
    query: String,
    modifier: Modifier = Modifier,
    placeholder: String = "Placeholder...",
    onQueryChange: (String) -> Unit,
    onSearch: (query: String) -> Unit = {query ->},
    enabled: Boolean = true,
) {
    var query by remember { mutableStateOf(query) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        // Main text field
        BasicTextField(
            value = query,
            onValueChange = {
                query = it
            },
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(28.dp)
                )
                .border(
                    width = 1.dp,
                    color = ExtendedTheme.colors.background25,
                    shape = RoundedCornerShape(28.dp)
                )
                .padding(
                    start = 20.dp,
                    end = if (query.isNotEmpty()) 88.dp else 56.dp, // Space for icons
                    top = 16.dp,
                    bottom = 16.dp
                ),
            enabled = enabled,
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
            decorationBox = { innerTextField ->
                if (query.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = ExtendedTheme.colors.background50,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                innerTextField()
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearch(query) })
        )

        // Icons container
        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Clear button - only show when there's text
            AnimatedVisibility(
                visible = query.isNotEmpty(),
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                IconButton(
                    onClick = { },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear search",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // Search button - properly contained within the search bar
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
                    .clickable { onSearch(query) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

/**
 * Previews for the SearchBar composable in both light and dark modes.
 * These previews help visualize the search bar's appearance with and without a query value.
 */
@Preview(showBackground = true)
@Composable
fun SearchBarPreviewLightMode() {
    TaskSpacesTheme {
        ExtendedColors {
            SearchBar(
                query = "",
                onQueryChange = {},
                placeholder = "Search...",
            )
        }
    }
}

/** * Preview of the SearchBar with a value in light mode.
 * This shows how the search bar looks when it has text input.
 */
@Preview(showBackground = true)
@Composable
fun SearchBarWithValuePreviewLightMode() {
    TaskSpacesTheme {
        ExtendedColors {
            SearchBar(
                query = "Value example",
                onQueryChange = {},
                placeholder = "Search...",
            )
        }
    }
}

/**
 * Previews for the SearchBar composable in dark mode.
 * These previews help visualize the search bar's appearance with and without a query value in a dark theme.
 */
@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun SearchBarPreviewDarkMode() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            SearchBar(
                query = "",
                onQueryChange = {},
                placeholder = "Search...",
            )
        }
    }
}

/** Preview of the SearchBar with a value in dark mode.
 * This shows how the search bar looks when it has text input in a dark theme.
 */
@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun SearchBarWithValuePreviewDarkMode() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            SearchBar(
                query = "Value example",
                onQueryChange = {},
                placeholder = "Search...",
            )
        }
    }
}