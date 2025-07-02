package com.ucapdm2025.taskspaces.ui.components.general

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.ucapdm2025.taskspaces.ui.screens.workspace.UiEvent
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme


/**
 * A composable that renders a styled message card used to display success or error feedback.
 *
 * @param message The message text to be shown inside the notification.
 * @param isSuccess Whether the message represents a success (`true`) or error (`false`) state.
 */
@Composable
private fun NotificationMessage(message: String, isSuccess: Boolean) {
    val icon       = if (isSuccess) Icons.Default.CheckCircle else Icons.Default.Info
    val iconTint   = if (isSuccess) Color(0xFF1B873F) else Color(0xFFB00020)
    val bgColor    = Color(0xFFF0F0F0)
    val textColor  = Color.Black

    Surface(
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 6.dp,
        color = bgColor
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = message,
                color = textColor,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

/**
 * A composable that displays a floating notification banner.
 * It animates in/out vertically and overlays the main content without displacing layout.
 *
 * This is typically used for showing transient messages (e.g., success or error states)
 * after user actions such as creation, update, or deletion.
 *
 * @param event The UI event containing the message and status type.
 * @param modifier Optional [Modifier] for further layout customization.
 * @param topPadding Top spacing below the app bar or title to anchor the notification.
 */
@Composable
fun NotificationHost(
    event: UiEvent?,
    modifier: Modifier = Modifier,
    topPadding: Dp = 96.dp
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .zIndex(10f),
        contentAlignment = Alignment.TopCenter
    ) {
        AnimatedVisibility(
            visible = event != null,
            enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
            exit  = slideOutVertically(targetOffsetY = { -it }) + fadeOut()
        ) {
            event?.let {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = topPadding)
                ) {
                    NotificationMessage(
                        message   = when (it) {
                            is UiEvent.Success -> it.message
                            is UiEvent.Error   -> it.message
                        },
                        isSuccess = it is UiEvent.Success
                    )
                }
            }
        }
    }
}

/**
 * Preview of a success notification in light theme.
 */
@Preview(showBackground = true)
@Composable
fun NotificationSuccessPreviewLight() {
    TaskSpacesTheme {
        ExtendedColors {
            NotificationHost(
                event = UiEvent.Success("Project created successfully")
            )
        }
    }
}
/**
 * Preview of an error notification in light theme.
 */
@Preview(showBackground = true)
@Composable
fun NotificationErrorPreviewLight() {
    TaskSpacesTheme {
        ExtendedColors {
            NotificationHost(
                event = UiEvent.Error("Unable to create project")
            )
        }
    }
}
/**
 * Preview of a success notification in dark theme.
 */
@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun NotificationSuccessPreviewDark() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            NotificationHost(
                event = UiEvent.Success("Project created successfully")
            )
        }
    }
}
/**
 * Preview of an error notification in dark theme.
 */
@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun NotificationErrorPreviewDark() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            NotificationHost(
                event = UiEvent.Error("Unable to create project")
            )
        }
    }
}
