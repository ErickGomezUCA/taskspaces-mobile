package com.ucapdm2025.taskspaces.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ucapdm2025.taskspaces.ui.components.general.Container
import com.ucapdm2025.taskspaces.ui.screens.home.sections.AssignedTasksSection
import com.ucapdm2025.taskspaces.ui.screens.home.sections.SharedWorkspacesSection
import com.ucapdm2025.taskspaces.ui.screens.home.sections.YourWorkspacesSection
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

/**
 * A composable function that represents the main home screen of the app.
 * Sections are visually grouped using the [Container] composable,
 * and styled with themed colors via [ExtendedTheme] and [MaterialTheme].
 */
@Composable
fun HomeScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        item {
            Container(title = "Your workspaces") {
                YourWorkspacesSection()
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Container(title = "Workspaces shared with me") {
                SharedWorkspacesSection()
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Container(title = "Assigned tasks") {
                AssignedTasksSection()
            }
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

/**
 * Preview of the HomeScreen in light mode using theme colors.
 */
@Preview(showBackground = true)
@Composable
fun HomeScreenLightPreview() {
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            HomeScreen()
        }
    }
}

/**
 * Preview of the HomeScreen in dark mode using theme colors.
 */
@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun HomeScreenDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            HomeScreen()
        }
    }
}
