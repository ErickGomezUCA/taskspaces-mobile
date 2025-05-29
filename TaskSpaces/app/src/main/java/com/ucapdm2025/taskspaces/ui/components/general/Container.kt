package com.ucapdm2025.taskspaces.ui.components.general

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

/**
 * A composable function that displays a container with a title and optional options button.
 *
 * @param title The title of the container.
 * @param modifier A [Modifier] for customizing the layout or behavior of the container.
 * @param showOptionsButton Whether to show the options button (default is true).
 * @param content The content to be displayed inside the container.
 */
@Composable
fun Container(
    title: String,
    modifier: Modifier = Modifier,
    showOptionsButton: Boolean = true,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = ExtendedTheme.colors.background05,
                shape = RoundedCornerShape(size = 24.dp)
            )
            .border(
                width = 1.dp,
                color = ExtendedTheme.colors.background25,
                shape = RoundedCornerShape(size = 24.dp)
            )
            .padding(24.dp)
    ) {
//        Container title
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                title,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (showOptionsButton) {
                IconButton(
                    onClick = { /* TODO: This will become a button to open a menu (e.g., dropdown for project actions). */ },
                    modifier = Modifier
                        .height(24.dp)
                        .padding(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreHoriz, contentDescription = "More options",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

//        Container body
        content()

    }
}

/**
 * A preview composable for the [Container] component.
 *
 * Displays a sample container with a title and content, allowing developers to visualize the layout and styling during design time.
 */
@Preview(showBackground = true)
@Composable
fun ContainerPreviewLightMode() {
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            Container(title = "Container Title") {
                Text(text = "Container Preview", color = MaterialTheme.colorScheme.onSurface)
            }
        }
    }
}

/**
 * A preview composable for the [Container] component in dark mode.
 *
 * Displays a sample container with a title and content, allowing developers to visualize the layout and styling during design time in dark mode.
 */
@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun ContainerPreviewDarkMode() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            Container(title = "Container Title") {
                Text(text = "Container Preview", color = MaterialTheme.colorScheme.onSurface)
            }
        }
    }
}

