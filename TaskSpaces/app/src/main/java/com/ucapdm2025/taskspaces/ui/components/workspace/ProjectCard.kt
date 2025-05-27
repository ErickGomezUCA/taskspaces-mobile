package com.ucapdm2025.taskspaces.ui.components.workspace

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucapdm2025.taskspaces.ui.theme.PrimaryLight25

/**
 * A reusable composable that represents a single project card.
 *
 * Displays an icon and a project name.
 *
 * @param name The name of the project to display.
 */
@Composable
fun ProjectCard(name: String) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                // TODO: Add navigation to project details screen when clicked
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(PrimaryLight25),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                // TODO: Replace with the exact icon from Figma or import as SVG.
                imageVector = Icons.Outlined.StarBorder,
                contentDescription = "Project Icon",
                tint = Color.Black,
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(name, fontSize = 14.sp, color = Color.Black)
    }
}
