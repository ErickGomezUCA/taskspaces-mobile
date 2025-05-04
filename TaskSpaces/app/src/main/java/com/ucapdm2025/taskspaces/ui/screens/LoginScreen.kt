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
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

@Composable
fun LoginScreen(navController: NavHostController) {
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(text= "Login Screen")
            Button(onClick = {
                navController.navigate(AppRoute)
            }) {
                Text(text = "Go to home")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    TaskSpacesTheme {
        LoginScreen(
            rememberNavController()
        )
    }
}