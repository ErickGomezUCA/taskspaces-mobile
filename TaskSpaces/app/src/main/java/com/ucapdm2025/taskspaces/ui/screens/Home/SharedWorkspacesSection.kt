package com.ucapdm2025.taskspaces.ui.screens.Home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme

@Composable
fun SharedWorkspacesSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        WorkspaceCard(name = "Workspace 3", projectsCount = 1, membersCount = 1)
        Spacer(modifier = Modifier.height(8.dp))

        WorkspaceCard(name = "Workspace 4", projectsCount = 1, membersCount = 1)
        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { /* View more shared workspaces */ },
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
