package com.ucapdm2025.taskspaces.ui.components.general

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

/**
 * A composable function that displays a floating status dialog.
 * This dialog is typically used to show status messages or notifications
 * at the bottom of the screen.
 *
 * @param onClose Callback invoked when the close button is clicked.
 * @param message The message to display in the dialog.
 */
@Composable
fun FloatingStatusDialog(
    onClose: () -> Unit,
    message: String = "Status message"
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            tonalElevation = 16.dp,
            color = ExtendedTheme.colors.background100,
            border = _root_ide_package_.androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.background)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 8.dp),
            ) {
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.padding(start = 8.dp)
                )
                IconButton(onClick = onClose, modifier = Modifier.padding(start = 8.dp)) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.background
                    )
                }
            }
        }
    }
}

/**
 * Preview for the [FloatingStatusDialog] in light theme.
 * Displays a sample status message and a close button.
 */
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun FloatingStatusDialogLightPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            FloatingStatusDialog(
                message = "Status message",
                onClose = {}
            )

        }
    }
}

/**
 * Preview for the [FloatingStatusDialog] in dark theme.
 * Displays a sample status message and a close button.
 */
@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun FloatingStatusDialogDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            FloatingStatusDialog(
                message = "Status message",
                onClose = {}
            )

        }
    }
}

