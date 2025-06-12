package com.ucapdm2025.taskspaces.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ucapdm2025.taskspaces.ui.screens.BookmarksScreen
import com.ucapdm2025.taskspaces.ui.screens.HomeScreen
import com.ucapdm2025.taskspaces.ui.screens.SearchScreen
import com.ucapdm2025.taskspaces.ui.screens.UserScreen
import com.ucapdm2025.taskspaces.ui.screens.project.ProjectScreen
import com.ucapdm2025.taskspaces.ui.screens.workspace.WorkspaceScreen


@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = ProjectRoute(projectId = 1)) {
//        TODO: Implement all views by their routes here
        composable<HomeRoute> {
            HomeScreen()
        }

        composable<WorkspaceRoute> { backStackEntry ->
//                Use this ID to get the workspace
            val workspaceId: Int = backStackEntry.arguments?.getInt("workspaceId") ?: 0
//            Workspace screen goes here
            WorkspaceScreen(workspaceId = workspaceId, onProjectCardClick = { projectId ->
                navController.navigate(ProjectRoute(projectId))
            })
        }

        composable<ProjectRoute> { backStackEntry ->
//                Use this ID to get the project
            val projectId: Int = backStackEntry.arguments?.getInt("projectId") ?: 0
//            Project screen goes here
            ProjectScreen(projectId = projectId)
        }

        composable<TaskRoute> { backStackEntry ->
//                Use this ID to get the task
//                TODO: See if this is correct, because tasks are handled by a dialog instead of a view
            backStackEntry.arguments?.getInt("taskId") ?: 0
//            Task screen goes here
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