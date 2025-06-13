package com.ucapdm2025.taskspaces.ui.screens.Home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme

@Composable
fun YourWorkspacesSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        WorkspaceCard(name = "Workspace 1", projectsCount = 1, membersCount = 1)
        Spacer(modifier = Modifier.height(8.dp))

        WorkspaceCard(name = "Workspace 2", projectsCount = 1, membersCount = 1)

        Spacer(modifier = Modifier.height(12.dp))

        // Create workspace button (centered)
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            TextButton(onClick = { /* Create workspace action */ }) {
                Text(
                    text = "Create workspace +",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // See more button
        Button(
            onClick = { /* Navigate to full list */ },
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
