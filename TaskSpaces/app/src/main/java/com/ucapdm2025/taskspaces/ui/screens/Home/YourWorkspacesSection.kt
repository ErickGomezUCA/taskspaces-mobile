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
import com.ucapdm2025.taskspaces.ui.screens.Home.WorkspaceCard

@Composable
fun YourWorkspacesSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(16.dp)) // fondo gris claro
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Your workspaces",
                style = MaterialTheme.typography.titleMedium
            )
            IconButton(onClick = {  }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More"
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))


        WorkspaceCard(name = "Workspace 1", projectsCount = 1, membersCount = 1)
        Spacer(modifier = Modifier.height(8.dp))
        WorkspaceCard(name = "Workspace 2", projectsCount = 1, membersCount = 1)

        Spacer(modifier = Modifier.height(12.dp))


        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            TextButton(onClick = { }) {
                Text(text = "Create workspace +", fontSize = 14.sp)
            }
        }



        Button(
            onClick = {  },
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
