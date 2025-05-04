package com.ucapdm2025.taskspaces.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ucapdm2025.taskspaces.ui.navigation.LoginRoute
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

/**
 * A composable function that displays the onboarding screen.
 *
 * @param navController The [NavHostController] used to navigate to the login screen.
 */
@Composable
fun OnboardingScreen(navController: NavHostController) {
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(text= "Onboarding Screen")
            Button(onClick = {
                navController.navigate(LoginRoute)
            }) {
                Text(text = "Go to login")
            }
        }
    }
}

/**
 * A composable function that displays the onboarding screen.
 *
 * @param navController The [NavHostController] used to navigate to the login screen.
 */
@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    TaskSpacesTheme {
        OnboardingScreen(
            rememberNavController()
        )
    }
}