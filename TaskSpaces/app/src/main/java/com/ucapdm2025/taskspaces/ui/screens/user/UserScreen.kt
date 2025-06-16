package com.ucapdm2025.taskspaces.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

// TODO: Implement the UserScreen UI
/** A composable function that displays the user screen.
 * Currently, it shows a simple text message.
 *
 * @param modifier A [Modifier] for customizing the layout or behavior of the user screen.
 */
@Composable
fun UserScreen() {
    Column {
        Text(text = "User Screen", color = MaterialTheme.colorScheme.onBackground)
    }
}

@Preview(showBackground = true)
@Composable
fun UserScreenLightPreview() {
    TaskSpacesTheme {
        UserScreen()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun UserScreenDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        UserScreen()
    }
}