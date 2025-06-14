package com.ucapdm2025.taskspaces.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ucapdm2025.taskspaces.ui.screens.BookmarksScreen
import com.ucapdm2025.taskspaces.ui.screens.home.HomeScreen
import com.ucapdm2025.taskspaces.ui.screens.SearchScreen
import com.ucapdm2025.taskspaces.ui.screens.UserScreen
import com.ucapdm2025.taskspaces.ui.screens.project.ProjectScreen
import com.ucapdm2025.taskspaces.ui.screens.test.home.TestHomeScreen
import com.ucapdm2025.taskspaces.ui.screens.test.login.TestLoginScreen
import com.ucapdm2025.taskspaces.ui.screens.workspace.WorkspaceScreen


@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = HomeRoute) {
//        TODO: Implement all views by their routes here
        composable<HomeRoute> {
            HomeScreen()
        }

        composable<WorkspaceRoute> { backStackEntry ->
            val workspaceId: Int = backStackEntry.arguments?.getInt("workspaceId") ?: 0
            WorkspaceScreen(workspaceId = workspaceId, onProjectCardClick = { projectId ->
                navController.navigate(ProjectRoute(projectId))
            })
        }

        composable<ProjectRoute> { backStackEntry ->
            val projectId: Int = backStackEntry.arguments?.getInt("projectId") ?: 0
            ProjectScreen(projectId = projectId)
        }

        composable<TimeTrackerRoute> { backStackEntry ->
            backStackEntry.arguments?.getInt("taskId") ?: 0
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