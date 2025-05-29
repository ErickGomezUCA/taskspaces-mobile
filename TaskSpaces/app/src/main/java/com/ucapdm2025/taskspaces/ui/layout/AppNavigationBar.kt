package com.ucapdm2025.taskspaces.ui.layout

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.ucapdm2025.taskspaces.ui.theme.Black25
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

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    ) {
        navItems.forEachIndexed { index, item ->
            val isSelected = selectedItem == index

            NavigationBarItem(
                label = { Text(text = item.label) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.filledIcon else item.outlinedIcon,
                        contentDescription = item.label,
                        tint = MaterialTheme.colorScheme.onPrimary
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
                colors = NavigationBarItemColors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    selectedIndicatorColor = Black25,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    disabledIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                    disabledTextColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppNavigationBarLightPreview() {
    val navController = rememberNavController()

    TaskSpacesTheme {
        AppNavigationBar(navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
fun AppNavigationBarDarkPreview() {
    val navController = rememberNavController()

    TaskSpacesTheme(darkTheme = true) {
        AppNavigationBar(navController = navController)
    }
}