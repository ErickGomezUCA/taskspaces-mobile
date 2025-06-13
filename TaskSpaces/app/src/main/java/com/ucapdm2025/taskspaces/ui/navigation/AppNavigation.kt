package com.ucapdm2025.taskspaces.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ucapdm2025.taskspaces.ui.screens.BookmarksScreen
import com.ucapdm2025.taskspaces.ui.screens.home.HomeScreen
import com.ucapdm2025.taskspaces.ui.screens.SearchScreen
import com.ucapdm2025.taskspaces.ui.screens.UserScreen


@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = HomeRoute) {
//        TODO: Implement all views by their routes here
        composable<HomeRoute> {
            HomeScreen()
        }

        composable<WorkspaceRoute> { backStackEntry ->
//                Use this ID to get the workspace
            backStackEntry.arguments?.getString("workspaceId") ?: 0
//            Workspace screen goes here
        }

        composable<ProjectRout> { backStackEntry ->
//                Use this ID to get the project
            backStackEntry.arguments?.getString("projectId") ?: 0
//            Project screen goes here
        }

        composable<TaskRoute> { backStackEntry ->
//                Use this ID to get the task
//                TODO: See if this is correct, because tasks are handled by a dialog instead of a view
            backStackEntry.arguments?.getString("taskId") ?: 0
//            Task screen goes here
        }

        composable<TimeTrackerRoute> { backStackEntry ->
            backStackEntry.arguments?.getString("taskId") ?: 0
//            Time Tracker screen goes here
        }

        composable<SearchRoute> {
            SearchScreen()
        }

        composable<BookmarksRoute> {
            BookmarksScreen()
        }

        composable<UserRoute> {
            UserScreen()
        }

        composable<SettingsRoute> {
//            Settings screen goes here
        }

        composable<ChangePasswordRoute> {
//            Change Password screen goes here
        }
    }
}