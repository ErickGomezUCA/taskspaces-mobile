package com.ucapdm2025.taskspaces.ui.screens.Home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ucapdm2025.taskspaces.ui.components.projects.TaskCard
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import com.ucapdm2025.taskspaces.ui.components.general.Tag

@Composable
fun AssignedTasksSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        TaskCard(
            title = "Task Title",
            tags = listOf(
                Tag("Tag", Color(0xFFE63946)),
                Tag("Tag", Color(0xFF2ECC71))
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        TaskCard(
            title = "Task Title",
            tags = listOf(
                Tag("Tag", Color(0xFFE67E22)),
                Tag("Tag", Color(0xFF27AE60))
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        TaskCard(
            title = "Task Title",
            tags = listOf(
                Tag("Tag", Color(0xFF9B59B6)),
                Tag("Tag", Color(0xFF3498DB))
            )
        )
        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { /* See more tasks */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = ExtendedTheme.colors.primary25,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("See more")
        }
    }
}
