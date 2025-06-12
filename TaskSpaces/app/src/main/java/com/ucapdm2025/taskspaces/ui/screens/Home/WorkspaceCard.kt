package com.ucapdm2025.taskspaces.ui.screens.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

/**
 * A composable that displays a workspace card.
 *
 * Includes a colored header block, workspace name, and project/member count.
 * Designed to be used in sections such as "Your workspaces" or "Shared with me".
 *
 * @param name The name of the workspace.
 * @param projectsCount Number of projects in the workspace.
 * @param membersCount Number of members in the workspace.
 * @param modifier Optional [Modifier] for layout adjustments.
 * @param onClick Callback triggered when the card is clicked.
 */
@Composable
fun WorkspaceCard(
    name: String,
    projectsCount: Int,
    membersCount: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ExtendedTheme.colors.primary25),
        onClick = onClick
    ) {
        Column {
            // Top block
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(ExtendedTheme.colors.primary50)
            )

            // Body
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$projectsCount Project(s)  •  $membersCount Member(s)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = ExtendedTheme.colors.background75
                )
            }
        }
    }
}

/**
 * Preview of [WorkspaceCard] in light mode.
 */
@Preview(showBackground = true)
@Composable
fun WorkspaceCardPreviewLight() {
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            Surface {
                WorkspaceCard(
                    name = "Design Sprint Board",
                    projectsCount = 3,
                    membersCount = 5
                )
            }
        }
    }
}

/**
 * Preview of [WorkspaceCard] in dark mode.
 */
@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun WorkspaceCardPreviewDark() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            Surface {
                WorkspaceCard(
                    name = "Marketing Plan",
                    projectsCount = 2,
                    membersCount = 7
                )
            }
        }
    }
}
