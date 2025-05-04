package com.ucapdm2025.taskspaces.ui.layout

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
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
import com.ucapdm2025.taskspaces.ui.navigation.BookmarksRoute
import com.ucapdm2025.taskspaces.ui.navigation.HomeRoute
import com.ucapdm2025.taskspaces.ui.navigation.SearchRoute
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

// A data class representing a navigation item in the navigation bar.
data class NavItem(
    val label: String,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector,
    val route: Any
)

/**
 * A composable function that displays a custom navigation bar.
 *
 * @param navController The [NavHostController] used to manage navigation between screens.
 */
@Composable
fun AppNavigationBar(navController: NavHostController) {
    val navItems = listOf(
        NavItem(
            label = "Home",
            filledIcon = Icons.Filled.Home,
            outlinedIcon = Icons.Outlined.Home,
            route = HomeRoute
        ),
        NavItem(
            label = "Search",
            filledIcon = Icons.Filled.Search,
            outlinedIcon = Icons.Outlined.Search,
            route = SearchRoute
        ),
        NavItem(
            label = "Bookmarks",
            filledIcon = Icons.Filled.Bookmark,
            outlinedIcon = Icons.Outlined.BookmarkBorder,
            route = BookmarksRoute
        )
    )

    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    NavigationBar {
        navItems.forEachIndexed { index, item ->
            val isSelected = selectedItem == index

            NavigationBarItem(
                label = { Text(item.label) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.filledIcon else item.outlinedIcon,
                        contentDescription = item.label
                    )
                },
                selected = isSelected,
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