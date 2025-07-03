package com.ucapdm2025.taskspaces.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ucapdm2025.taskspaces.ui.components.onboarding.SplashScreen
import com.ucapdm2025.taskspaces.ui.layout.AppScaffold
import com.ucapdm2025.taskspaces.ui.screens.LoginScreen
import com.ucapdm2025.taskspaces.ui.screens.OnboardingScreen
import com.ucapdm2025.taskspaces.ui.screens.SignUpScreen

/**
 * A composable function that sets up the navigation graph for the onboarding flow.
 * Includes routes for onboarding, login, signup, and the main app scaffold.
 */
@Composable
fun OnboardingNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = SplashRoute) {
        composable<SplashRoute> {
            SplashScreen(navController = navController)
        }

        composable<OnboardingRoute> {
            OnboardingScreen(navController = navController)
        }

        composable<LoginRoute> {
            LoginScreen(
                onSuccessfulLogin = { navController.navigate(AppRoute) },
                onNavigateToSignUp = { navController.navigate(SignupRoute) }
            )
        }

        composable<SignupRoute> {
            SignUpScreen(
                onSignUp = { navController.navigate(AppRoute) },
                onNavigateToLogin = { navController.popBackStack(LoginRoute, false) }
            )
        }

//        Home, workspace, project, tasks, search, bookmarks and user will go in this route
//        Inside of AppScaffold there is AppNavigation, where the bottom bar handles the
//        navigation between all those screens
        composable<AppRoute> {
            AppScaffold()
        }
    }
}