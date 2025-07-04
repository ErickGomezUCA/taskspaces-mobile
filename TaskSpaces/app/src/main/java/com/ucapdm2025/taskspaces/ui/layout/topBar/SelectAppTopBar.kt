package com.ucapdm2025.taskspaces.ui.layout.topBar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ucapdm2025.taskspaces.TaskSpacesApplication
import com.ucapdm2025.taskspaces.helpers.SearchHolder
import com.ucapdm2025.taskspaces.ui.navigation.UserRoute
import com.ucapdm2025.taskspaces.ui.screens.home.HomeViewModel
import com.ucapdm2025.taskspaces.ui.screens.search.SearchViewModel
import com.ucapdm2025.taskspaces.ui.screens.search.SearchViewModelFactory
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

@Composable
fun SelectAppTopBar(currentRoute: String, navController: NavHostController) {
    val application = LocalContext.current.applicationContext as TaskSpacesApplication
    val searchRepository = application.appProvider.provideSearchRepository()
    val viewModel: SearchViewModel = viewModel(
        factory = SearchViewModelFactory(searchRepository),
    )
    val homeViewModel: HomeViewModel = viewModel(
        factory = HomeViewModel.Factory
    )
    val currentUserName = homeViewModel.currentUserName.collectAsStateWithLifecycle()
    val searchResults = viewModel.searchResults.collectAsStateWithLifecycle()

    LaunchedEffect(searchResults.value) {
        SearchHolder.results.value = searchResults.value
    }

    when (currentRoute) {
        "HomeRoute" -> {
            AppTopBar(
                title = if (currentUserName.value == "") "Welcome" else "Welcome, ${currentUserName.value.split(" ").first()}",
                onUserClick = { navController.navigate(UserRoute) },
                variant = AppTopBarVariant.HOME
            )
        }

        "SearchRoute" -> {
            AppTopBarWithSearchBar(
                query = "",
                placeholder = "Search...",
                onQueryChange = {
                    viewModel.setQuery(it)
                },
                onSearch = {
                    viewModel.search(it)
                    SearchHolder.searchQuery.value = it
                })
        }

        "BookmarksRoute" -> {
            AppTopBar(
                title = "Your Bookmarks",
                onReturn = { navController.popBackStack() },
                onUserClick = { navController.navigate(UserRoute) },
                variant = AppTopBarVariant.DEFAULT
            )
        }

        "WorkspaceRoute",
        "ProjectRoute",
        "TimeTrackerRoute",
        "UserRoute",
        "SettingsRoute",
        "ChangePasswordRoute" -> {
            AppTopBar(
                title = "Return",
                onReturn = { navController.popBackStack() },
                onUserClick = { navController.navigate(UserRoute) },
                variant = AppTopBarVariant.NAVIGATION
            )
        }

        else -> {
            AppTopBar(
                title = "Top App Bar",
                onReturn = { navController.popBackStack() },
                onUserClick = { navController.navigate(UserRoute) },
                variant = AppTopBarVariant.DEFAULT
            )
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