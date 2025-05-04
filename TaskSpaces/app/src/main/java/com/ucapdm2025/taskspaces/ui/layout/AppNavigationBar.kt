package com.ucapdm2025.taskspaces.ui.layout

import androidx.compose.runtime.Composable
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

// A data class representing a navigation item in the navigation bar.
data class NavItem(val label: String, val icon: ImageVector, val route: Any)

/**
 * A composable function that displays a custom navigation bar.
 *
 * @param navController The [NavHostController] used to manage navigation between screens.
 */
@Composable
fun AppNavigationBar(navController: NavHostController) {
    val navItems =
//        listOf(
//        NavItem("Home", Icons.Outlined.Home, MainRoute),
//        NavItem("Search", Icons.Outlined.Search, SearchRoute),
//        NavItem("Bookmarks", Icons.Outlined.Bookmark, MyOrdersRoute)
//        )
        emptyList<NavItem>()

    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    NavigationBar {
        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                label = { Text(item.label) },
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}

/**
 * A preview of the [AppNavigationBar] composable.
 * Demonstrates the navigation bar with a default theme.
 */
@Preview(showBackground = true)
@Composable
fun AppNavigationBarPreview() {
    val navController = rememberNavController()

    TaskSpacesTheme {
        AppNavigationBar(navController = navController)
    }
}