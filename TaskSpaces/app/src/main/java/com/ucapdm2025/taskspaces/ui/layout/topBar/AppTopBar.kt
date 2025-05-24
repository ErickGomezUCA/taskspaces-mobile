package com.ucapdm2025.taskspaces.ui.layout.topBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme
import com.ucapdm2025.taskspaces.utils.getTopAppBarContainerColor
import com.ucapdm2025.taskspaces.utils.getTopAppBarContentColor

/**
 * Top bar for the app, used in all screens.
 * @param title The title to show in the top bar.
 */
// TODO: Define a way to make variants of the same top bar based on the current route
@OptIn(ExperimentalMaterial3Api::class)
@Composable
//TODO: Determine a way to show go back icon or hide it
fun AppTopBar(title: String, variant: TopAppBarVariant = TopAppBarVariant.DEFAULT) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = getTopAppBarContainerColor(variant),
            titleContentColor = getTopAppBarContentColor(variant),
        ),
        title = { Text(text = title) },
        navigationIcon = {
            if (variant == TopAppBarVariant.NAVIGATION) {
                IconButton(onClick = { /* TODO: Handle back navigation */ }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Back",
                        tint = getTopAppBarContentColor(variant)
                    )
                }
            }
        },
        actions = {
//            TODO: Go to user page
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "User",
                    tint = getTopAppBarContentColor(variant)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AppTopBarDefaultLightPreview() {
    TaskSpacesTheme {
        AppTopBar(title = "Title example")
    }
}

@Preview(showBackground = true)
@Composable
fun AppTopBarHomeLightPreview() {
    TaskSpacesTheme {
        AppTopBar(title = "Title example", variant = TopAppBarVariant.HOME)
    }
}

@Preview(showBackground = true)
@Composable
fun AppTopBarNavigationLightPreview() {
    TaskSpacesTheme {
        AppTopBar(title = "Title example", variant = TopAppBarVariant.NAVIGATION)
    }
}

@Preview(showBackground = true)
@Composable
fun AppTopBarDefaultDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        AppTopBar(title = "Title example")
    }
}

@Preview(showBackground = true)
@Composable
fun AppTopBarHomeDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        AppTopBar(title = "Title example", variant = TopAppBarVariant.HOME)
    }
}

@Preview(showBackground = true)
@Composable
fun AppTopBarNavigationDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        AppTopBar(title = "Title example", variant = TopAppBarVariant.NAVIGATION)
    }
}