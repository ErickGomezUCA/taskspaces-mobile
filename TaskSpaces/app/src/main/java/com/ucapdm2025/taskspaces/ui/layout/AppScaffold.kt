package com.ucapdm2025.taskspaces.ui.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ucapdm2025.taskspaces.ui.layout.topBar.SelectAppTopBar
import com.ucapdm2025.taskspaces.ui.navigation.AppNavigation
import com.ucapdm2025.taskspaces.ui.screens.home.HomeViewModel
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme
import com.ucapdm2025.taskspaces.utils.getCurrentRoute

/**
 * A composable function that provides the main scaffold layout for the app.
 * Includes a bottom navigation bar and a content area for navigation.
 */

@Composable
fun AppScaffold() {
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = getCurrentRoute(navBackStackEntry) ?: "UnknownRoute"

    val viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
    val userNameState = viewModel.userName.collectAsStateWithLifecycle()
    val firstName = userNameState.value.split(" ").firstOrNull() ?: "User"

    Scaffold(
        topBar = {
            SelectAppTopBar(
                currentRoute = currentRoute,
                navController = navController,
                firstName = firstName
            )
        },
        bottomBar = { AppNavigationBar(navController = navController) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            AppNavigation(navController = navController)
        }
    }
}


/**
 * A preview of the [AppScaffold] composable.
 * Demonstrates the scaffold with a default theme.
 */
@Preview(showBackground = true)
@Composable
fun AppScaffoldLightPreview() {
    TaskSpacesTheme {
        AppScaffold()
    }
}

@Preview(showBackground = true)
@Composable
fun AppScaffoldDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        AppScaffold()
    }
}