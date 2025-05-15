package com.ucapdm2025.taskspaces.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

/**
 * A composable function that creates a container with a rounded corner shape and a background color.
 *
 * @param modifier The [Modifier] to be applied to the container.
 * @param content The content to be displayed inside the container.
 */
@Composable
fun Container(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color(0x0D21232B),
                shape = RoundedCornerShape(size = 24.dp)
            )
            .border(width = 1.dp, color = Color(0x4021232B), shape = RoundedCornerShape(size = 24.dp))
            .padding(24.dp)
    ) { content() }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun ContainerPreview() {
    TaskSpacesTheme(darkTheme = true) {
        Container {
            Text(text = "Container Preview", color = MaterialTheme.colorScheme.onSurface)
        }
    }
}