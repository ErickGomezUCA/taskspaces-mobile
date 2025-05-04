package com.ucapdm2025.taskspaces.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ucapdm2025.taskspaces.ui.navigation.LoginRoute
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

@Composable
fun OnboardingScreen(navController: NavHostController) {
    Column {
        Text(text= "Onboarding Screen")
        Button(onClick = {
            navController.navigate(LoginRoute)
        }) {
            Text(text = "Go to login")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    TaskSpacesTheme {
        OnboardingScreen(
            rememberNavController()
        )
    }
}