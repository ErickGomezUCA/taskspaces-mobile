package com.ucapdm2025.taskspaces.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
            .background(
                color = Color(0x0D21232B),
                shape = RoundedCornerShape(size = 24.dp)
            )
            .padding(24.dp)
    ) { content() }
}

@Preview(showBackground = true)
@Composable
fun ContainerPreview() {
    TaskSpacesTheme {
        Container {
            Text(text = "Container Preview")
        }
    }
}