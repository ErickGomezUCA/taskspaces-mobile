package com.ucapdm2025.taskspaces.ui.components.general

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

/**
 * A reusable container composable for grouping related search content (e.g., workspaces, projects).
 *
 * Displays a section title with an optional "View more" action, followed by custom content.
 *
 * @param title The section title displayed at the top.
 * @param modifier Modifier for customizing layout and styling.
 * @param onViewMoreClick Optional callback triggered when "View more" is clicked.
 * @param content Slot for injecting composables inside the container.
 */
@Composable
fun SearchContainer(
    title: String,
    modifier: Modifier = Modifier,
    onViewMoreClick: () -> Unit = {},
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
            .padding(15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

//            TODO: Reconsider to implement this
//            TextButton(onClick = onViewMoreClick) {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Text(
//                        text = "View more",
//                        fontSize = 20.sp,
//                        color = MaterialTheme.colorScheme.primary
//                    )
//                    Icon(
//                        imageVector = Icons.Default.ChevronRight,
//                        contentDescription = "Go to more",
//                        tint = MaterialTheme.colorScheme.primary
//                    )
//                }
//            }
        }

        Spacer(modifier = Modifier.height(24.dp))

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
fun SearchContainerPreviewLightMode() {
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            SearchContainer(title = "Container Title") {
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
fun SearchContainerPreviewDarkMode() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            SearchContainer(title = "Container Title") {
                Text(text = "Container Preview", color = MaterialTheme.colorScheme.onSurface)
            }
        }
    }
}

