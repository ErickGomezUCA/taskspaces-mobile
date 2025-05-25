package com.ucapdm2025.taskspaces.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.ucapdm2025.taskspaces.ui.components.projects.SharedWorkspacesSection
import com.ucapdm2025.taskspaces.ui.components.projects.YourWorkspacesSection
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

@Composable
fun HomeScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        item {
            Text(
                text = "Welcome, \$USER",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        item {
            YourWorkspacesSection()
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            SharedWorkspacesSection()
            Spacer(modifier = Modifier.height(80.dp)) // para evitar que el último botón quede tapado por la bottom bar
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenLightPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            HomeScreen()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun HomeScreenDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            HomeScreen()
        }
    }
}
