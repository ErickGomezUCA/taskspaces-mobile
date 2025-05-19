package com.ucapdm2025.taskspaces.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

@Composable
fun FeedbackIcon() {

}

@Preview(showBackground = true)
@Composable
fun FeedbackIconPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            FeedbackIcon()
        }
    }
}