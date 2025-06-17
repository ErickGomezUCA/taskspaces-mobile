package com.ucapdm2025.taskspaces.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ucapdm2025.taskspaces.ui.screens.bookmark.BookmarksScreen
import com.ucapdm2025.taskspaces.ui.screens.home.HomeScreen
import com.ucapdm2025.taskspaces.ui.screens.SearchScreen
import com.ucapdm2025.taskspaces.ui.screens.UserScreen
import com.ucapdm2025.taskspaces.ui.screens.project.ProjectScreen
import com.ucapdm2025.taskspaces.ui.screens.workspace.WorkspaceScreen


@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = HomeRoute) {
//        TODO: Implement all views by their routes here
        composable<HomeRoute> {
            HomeScreen(onNavigateWorkspace = { workspaceId ->
                navController.navigate(WorkspaceRoute(workspaceId))
            })
        }

        composable<WorkspaceRoute> { backStackEntry ->
            val workspaceId: Int = backStackEntry.arguments?.getInt("workspaceId") ?: 0
            WorkspaceScreen(workspaceId = workspaceId, onNavigateProject = { projectId ->
                navController.navigate(ProjectRoute(projectId))
            })
        }

        composable<ProjectRoute> { backStackEntry ->
            val projectId: Int = backStackEntry.arguments?.getInt("projectId") ?: 0
            // taskId is optional, used for navigating and opening a task dialog within a project
            val taskId: Int? = backStackEntry.arguments?.getInt("taskId")
            ProjectScreen(projectId = projectId, taskId = taskId)
        }

        composable<TimeTrackerRoute> { backStackEntry ->
            backStackEntry.arguments?.getInt("taskId") ?: 0
//            Time Tracker screen goes here
        }

        composable<SearchRoute> {
            SearchScreen()
        }

        composable<BookmarksRoute> {
            BookmarksScreen(onBookmarkedTaskClick = { projectId, taskId ->
                navController.navigate(ProjectRoute(projectId = projectId, taskId = taskId))
            })
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