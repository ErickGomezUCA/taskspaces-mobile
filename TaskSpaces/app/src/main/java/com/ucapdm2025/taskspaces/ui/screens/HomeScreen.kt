
package com.ucapdm2025.taskspaces.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ucapdm2025.taskspaces.ui.components.projects.YourWorkspacesSection
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme
import androidx.compose.ui.unit.dp


// TODO: Implement the HomeScreen UI
/**
 * A composable function that displays the home screen.
 * Currently, it shows a simple text message.
 *
 * @param modifier A [Modifier] for customizing the layout or behavior of the home screen.
 */
@Composable
fun HomeScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        //Text("Welcome, \$USER", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(12.dp))

        YourWorkspacesSection()
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