package com.ucapdm2025.taskspaces.ui.components.projects

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AssignedTasksSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Assigned tasks",
                style = MaterialTheme.typography.titleMedium
            )
            IconButton(onClick = { /* opciones */ }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More"
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

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
            onClick = { /* See more */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("See more")
        }
    }
}
