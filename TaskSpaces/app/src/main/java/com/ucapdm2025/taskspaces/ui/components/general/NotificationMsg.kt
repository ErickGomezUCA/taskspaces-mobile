package com.ucapdm2025.taskspaces.ui.components.general

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ucapdm2025.taskspaces.ui.screens.workspace.UiEvent
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

/**
 * Figma-style notification: white card with icon and message.
 */
@Composable
fun NotificationMessage(message: String, isSuccess: Boolean) {
    val icon = if (isSuccess) Icons.Default.CheckCircle else Icons.Default.Info
    val iconTint = if (isSuccess) Color(0xFF1B873F) else Color(0xFF8B0000)
    val backgroundColor = Color.White
    val textColor = Color.Black

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(10.dp))
            .background(backgroundColor, RoundedCornerShape(10.dp))
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Center)
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
 * Shows the [NotificationMessage] centered on screen using a smooth animation.
 */
@Composable
fun NotificationHost(
    event: UiEvent?,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = event != null,
        enter = slideInVertically { it / 2 } + fadeIn(),
        exit = slideOutVertically { it / 2 } + fadeOut(),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 200.dp)
    ) {
        event?.let {
            NotificationMessage(
                message = when (it) {
                    is UiEvent.Success -> it.message
                    is UiEvent.Error -> it.message
                },
                isSuccess = it is UiEvent.Success
            )
        }
    }
}

/* -------- Previews -------- */

/**
 * Preview of [NotificationMessage] success message in light theme.
 */
@Preview(showBackground = true)
@Composable
fun NotificationMessageSuccessPreviewLight() {
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            NotificationMessage(
                message = "Successfully completed the action",
                isSuccess = true
            )
        }
    }
}

/**
 * Preview of [NotificationMessage] error message in light theme.
 */
@Preview(showBackground = true)
@Composable
fun NotificationMessageErrorPreviewLight() {
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            NotificationMessage(
                message = "The action could not be completed",
                isSuccess = false
            )
        }
    }
}

/**
 * Preview of [NotificationMessage] success message in dark theme.
 */
@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun NotificationMessageSuccessPreviewDark() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            NotificationMessage(
                message = "Successfully completed the action",
                isSuccess = true
            )
        }
    }
}

/**
 * Preview of [NotificationMessage] error message in dark theme.
 */
@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun NotificationMessageErrorPreviewDark() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            NotificationMessage(
                message = "The action could not be completed",
                isSuccess = false
            )
        }
    }
}
