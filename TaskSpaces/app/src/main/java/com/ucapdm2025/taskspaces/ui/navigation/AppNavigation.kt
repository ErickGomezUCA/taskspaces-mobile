package com.ucapdm2025.taskspaces.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ucapdm2025.taskspaces.ui.components.onboarding.SplashScreen
import com.ucapdm2025.taskspaces.ui.screens.bookmark.BookmarksScreen
import com.ucapdm2025.taskspaces.ui.screens.home.HomeScreen
import com.ucapdm2025.taskspaces.ui.screens.search.SearchScreen

import com.ucapdm2025.taskspaces.ui.screens.user.ChangePasswordScreen
import com.ucapdm2025.taskspaces.ui.screens.user.SettingsScreen
import com.ucapdm2025.taskspaces.ui.screens.user.UserScreen
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

//            Fixed: Avoid opening a task dialog when taskId is 0 (it will show a task not found error)
            ProjectScreen(projectId = projectId, taskId = if (taskId == 0) null else taskId)
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
            UserScreen(
                onNavigateToSettings = {
                    navController.navigate(SettingsRoute)
                }
            )
        }

        composable<SettingsRoute> {
            SettingsScreen(
                onNavigateToChangePassword = {
                    navController.navigate(ChangePasswordRoute)
                }
            )
        }

        composable<ChangePasswordRoute> {
            ChangePasswordScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}