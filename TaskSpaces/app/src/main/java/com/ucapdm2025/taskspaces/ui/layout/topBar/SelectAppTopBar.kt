package com.ucapdm2025.taskspaces.ui.layout.topBar

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

@Composable
fun SelectAppTopBar(currentRoute: String) {
    when (currentRoute) {
        "HomeRoute" -> {
            AppTopBar(title = "Welcome, USER", variant = TopAppBarVariant.HOME)
        }

        "SearchRoute" -> {
            AppTopBar(title = "Search", variant = TopAppBarVariant.DEFAULT)
        }

        "BookmarksRoute" -> {
            AppTopBar(title = "Bookmarks", variant = TopAppBarVariant.DEFAULT)
        }

        "WorkspaceRoute",
        "ProjectRoute",
        "TaskRoute",
        "TimeTrackerRoute",
        "UserRoute",
        "SettingsRoute",
        "ChangePasswordRoute" -> {
//            TODO: Add go back action here
            AppTopBar(title = "Return", variant = TopAppBarVariant.NAVIGATION)
        }

        else -> {
            AppTopBar(title = "Top App Bar")
        }
    }
}

