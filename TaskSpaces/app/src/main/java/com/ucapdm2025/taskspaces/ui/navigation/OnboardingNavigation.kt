package com.ucapdm2025.taskspaces.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ucapdm2025.taskspaces.ui.layout.AppScaffold
import com.ucapdm2025.taskspaces.ui.screens.OnboardingScreen

@Composable
fun OnboardingNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = OnboardingRoute) {
        composable<OnboardingRoute> {
            OnboardingScreen(navController = navController)
        }

        composable<LoginRoute> {
//            Login screen goes here
        }

        composable<SignupRoute> {
//            Signup screen goes here
        }

        composable<AppRoute> {
            AppScaffold()
        }
    }
}