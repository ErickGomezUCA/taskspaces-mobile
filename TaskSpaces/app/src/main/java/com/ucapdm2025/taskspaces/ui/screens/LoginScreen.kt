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
import com.ucapdm2025.taskspaces.ui.navigation.AppRoute
import com.ucapdm2025.taskspaces.ui.navigation.LoginRoute
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

// TODO: Implement the LoginScreen UI
/**
 * A composable function that displays the login screen.
 *
 * @param navController The [NavHostController] used to navigate to the home screen.
 */
@Composable
fun LoginScreen(navController: NavHostController) {
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(text = "Login Screen")
//            Avoid returning to this screen when pressing back button
            Button(onClick = {
                navController.navigate(AppRoute) {
                    popUpTo(LoginRoute) { inclusive = true }
                    launchSingleTop = true
                }
            }) {
                Text(text = "Go to home")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenLightPreview() {
    TaskSpacesTheme {
        LoginScreen(
            rememberNavController()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        LoginScreen(
            rememberNavController()
        )
    }
}