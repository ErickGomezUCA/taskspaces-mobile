package com.ucapdm2025.taskspaces.ui.components.projects

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ucapdm2025.taskspaces.ui.theme.ProjectScreenBackground
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

/**
 * A composable layout that provides the standard background for the Projects screen.
 *
 * This component wraps its content with a full-screen column that applies the
 * specific background color used exclusively on the Projects screen, along with
 * consistent padding.
 *
 * Use this to ensure visual consistency across all content displayed on the Projects screen.
 *
 * @param modifier Optional [Modifier] to be applied to the layout.
 * @param content The composable content to be displayed within the background.
 */
@Composable
fun ProjectsBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ProjectScreenBackground)
            .padding(vertical = 16.dp, horizontal = 0.dp)
    ) {
        content()
    }
}

/**
 * A preview composable for the [ProjectsBackground] component.
 *
 * Displays the Projects screen background with system UI enabled,
 * allowing developers to visualize layout spacing and theming in design-time previews.
 */
@Preview(showSystemUi = true)
@Composable
fun ProjectsBackgroundPreview() {
    TaskSpacesTheme {
        ProjectsBackground(
            content = {

            }
        )
    }
}