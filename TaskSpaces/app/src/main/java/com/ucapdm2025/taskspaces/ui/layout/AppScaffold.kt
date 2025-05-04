package com.ucapdm2025.taskspaces.ui.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.ucapdm2025.taskspaces.ui.navigation.AppNavigation
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

@Composable
fun AppScaffold() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { AppNavigationBar(navController = navController) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            AppNavigation(navController = navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppScaffoldPreview() {
    TaskSpacesTheme {
        AppScaffold()
    }
}