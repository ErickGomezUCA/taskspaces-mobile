package com.ucapdm2025.taskspaces.ui.layout.topBar

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

@Composable
fun SelectAppTopBar(currentRoute: String, navController: NavHostController) {
    when (currentRoute) {
        "HomeRoute" -> {
            AppTopBar(
                title = "Welcome, USER",
                navController = navController,
                variant = AppTopBarVariant.HOME
            )
        }

//        TODO: Handle query change and search action
        "SearchRoute" -> {
            AppTopBarWithSearchBar(
                query = "",
                placeholder = "Search...",
                onQueryChange = {},
                onSearch = {})
        }

        //        TODO: Handle query change and search action
        "BookmarksRoute" -> {
            AppTopBarWithSearchBar(
                query = "",
                placeholder = "Search tasks...",
                onQueryChange = {},
                onSearch = {})
        }

        "WorkspaceRoute",
        "ProjectRoute",
        "TaskRoute",
        "TimeTrackerRoute",
        "UserRoute",
        "SettingsRoute",
        "ChangePasswordRoute" -> {
//            TODO: Add go back action here
            AppTopBar(
                title = "Return",
                navController = rememberNavController(),
                variant = AppTopBarVariant.NAVIGATION
            )
        }

        else -> {
            AppTopBar(title = "Top App Bar", navController = rememberNavController())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectAppTopBarHomeLightPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            SelectAppTopBar(currentRoute = "HomeRoute", navController = rememberNavController())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectAppTopBarSearchLightPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            SelectAppTopBar(currentRoute = "SearchRoute", navController = rememberNavController())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectAppTopBarBookmarksLightPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            SelectAppTopBar(
                currentRoute = "BookmarksRoute",
                navController = rememberNavController()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectAppTopBarWorkspaceLightPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            SelectAppTopBar(
                currentRoute = "WorkspaceRoute",
                navController = rememberNavController()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectAppTopBarDefaultLightPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            SelectAppTopBar(currentRoute = "", navController = rememberNavController())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectAppTopBarHomeDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            SelectAppTopBar(currentRoute = "HomeRoute", navController = rememberNavController())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectAppTopBarSearchDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            SelectAppTopBar(currentRoute = "SearchRoute", navController = rememberNavController())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectAppTopBarBookmarksDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            SelectAppTopBar(
                currentRoute = "BookmarksRoute",
                navController = rememberNavController()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectAppTopBarWorkspaceDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            SelectAppTopBar(
                currentRoute = "WorkspaceRoute",
                navController = rememberNavController()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectAppTopBarDefaultDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            SelectAppTopBar(currentRoute = "", navController = rememberNavController())
        }
    }
}