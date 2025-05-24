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

@Preview(showBackground = true)
@Composable
fun SelectAppTopBarHomeLightPreview() {
    TaskSpacesTheme {
        SelectAppTopBar(currentRoute = "HomeRoute")
    }
}

@Preview(showBackground = true)
@Composable
fun SelectAppTopBarSearchLightPreview() {
    TaskSpacesTheme {
        SelectAppTopBar(currentRoute = "SearchRoute")
    }
}

@Preview(showBackground = true)
@Composable
fun SelectAppTopBarBookmarksLightPreview() {
    TaskSpacesTheme {
        SelectAppTopBar(currentRoute = "BookmarksRoute")
    }
}

@Preview(showBackground = true)
@Composable
fun SelectAppTopBarWorkspaceLightPreview() {
    TaskSpacesTheme {
        SelectAppTopBar(currentRoute = "WorkspaceRoute")
    }
}

@Preview(showBackground = true)
@Composable
fun SelectAppTopBarDefaultLightPreview() {
    TaskSpacesTheme {
        SelectAppTopBar(currentRoute = "")
    }
}

@Preview(showBackground = true)
@Composable
fun SelectAppTopBarHomeDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        SelectAppTopBar(currentRoute = "HomeRoute")
    }
}

@Preview(showBackground = true)
@Composable
fun SelectAppTopBarSearchDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        SelectAppTopBar(currentRoute = "SearchRoute")
    }
}

@Preview(showBackground = true)
@Composable
fun SelectAppTopBarBookmarksDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        SelectAppTopBar(currentRoute = "BookmarksRoute")
    }
}

@Preview(showBackground = true)
@Composable
fun SelectAppTopBarWorkspaceDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        SelectAppTopBar(currentRoute = "WorkspaceRoute")
    }
}

@Preview(showBackground = true)
@Composable
fun SelectAppTopBarDefaultDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        SelectAppTopBar(currentRoute = "")
    }
}