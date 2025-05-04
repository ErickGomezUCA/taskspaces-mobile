package com.ucapdm2025.taskspaces.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = OnboardingRoute) {
//        TODO: Implement all views by their routes here
        composable<OnboardingRoute> {
//            Onboarding screen goes here
        }

        composable<LoginRoute> {
//            Login screen goes here
        }

        composable<SignupRoute> {
//            Signup screen goes here
        }

        composable<HomeRoute> {
//            Home screen goes here
        }

        composable<WorkspaceRoute> { backStackEntry ->
            val workspaceId = backStackEntry.arguments?.getString("workspaceId") ?: 0

//            Workspace screen goes here
        }

        composable<ProjectRout> { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("projectId") ?: 0
//            Project screen goes here
        }

        composable<TaskRoute> { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId") ?: 0
//            Task screen goes here
        }

        composable<TimeTrackerRoute> { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId") ?: 0
//            Time Tracker screen goes here
        }

        composable<SearchRoute> {
//            Search screen goes here
        }

        composable<BookmarksRoute> {
//            Bookmarks screen goes here
        }

        composable<UserRoute> {
//            User screen goes here
        }

        composable<SettingsRoute> {
//            Settings screen goes here
        }

        composable<ChangePasswordRoute> {
//            Change Password screen goes here
        }
    }
}