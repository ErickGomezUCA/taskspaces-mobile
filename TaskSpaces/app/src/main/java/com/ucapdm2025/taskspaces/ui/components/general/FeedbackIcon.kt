package com.ucapdm2025.taskspaces.ui.components.general

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme


/**
 * A composable function that displays a feedback icon with a circular background and a title.
 *
 * @param icon The [ImageVector] to be displayed as the icon inside the circular background.
 * @param title The text to be displayed below the icon, providing context or feedback to the user.
 * @param modifier The [Modifier] to be applied to the root layout of the composable.
 */
@Composable
fun FeedbackIcon(
    icon: ImageVector,
    title: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Circular background with icon
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(
                    color = ExtendedTheme.colors.background05,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = ExtendedTheme.colors.background50
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = title,
            fontSize = 16.sp,
            color = ExtendedTheme.colors.background50,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )
    }
}

/**
 * Previews for the [FeedbackIcon] composable in both light and dark themes.
 * These previews help visualize how the component looks in different themes.
 */
@Preview(showBackground = true)
@Composable
fun FeedbackIconPreviewLightMode() {
    TaskSpacesTheme {
        ExtendedColors {
            FeedbackIcon(
                icon = Icons.Default.Search,
                title = "Start searching workspaces, projects, tasks and users"
            )
        }
    }
}

/**
 * Preview for the [FeedbackIcon] composable in dark mode.
 * This preview helps visualize how the component looks in a dark theme.
 */
@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun FeedbackIconPreviewDarkMode() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            FeedbackIcon(
                icon = Icons.Default.Search,
                title = "Start searching workspaces, projects, tasks and users"
            )
        }
    }
}