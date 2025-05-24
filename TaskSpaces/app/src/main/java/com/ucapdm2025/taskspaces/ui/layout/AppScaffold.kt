package com.ucapdm2025.taskspaces.ui.layout

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ucapdm2025.taskspaces.ui.layout.topBar.AppTopBar
import com.ucapdm2025.taskspaces.ui.layout.topBar.SelectAppTopBar
import com.ucapdm2025.taskspaces.ui.navigation.AppNavigation
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme
import com.ucapdm2025.taskspaces.utils.getCurrentRoute

/**
 * A composable function that provides the main scaffold layout for the app.
 * Includes a bottom navigation bar and a content area for navigation.
 */
@Composable
fun AppScaffold() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = getCurrentRoute(navBackStackEntry) ?: "UnknownRoute"

    Log.d("test1", currentRoute)

    Scaffold(
        topBar = { SelectAppTopBar(currentRoute = currentRoute, navController = navController) },
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