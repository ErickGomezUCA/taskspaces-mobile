package com.ucapdm2025.taskspaces.ui.components.projects

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme
import com.ucapdm2025.taskspaces.ui.theme.White100

@Composable
fun TaskStatusColumn(
    status: StatusVariations,
    tasks: List<Task>,
    onAddTaskClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(color = White100, shape = RoundedCornerShape(16.dp))
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(16.dp),

        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TaskStatus(status)

        tasks.forEach { task ->
            TaskCard(
                title = task.title,
                tags = task.tags,
                onClick = {}
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(
                onClick = { onAddTaskClick() },
                contentPadding = PaddingValues(0.dp),
            ) {
                Text(
                    text = "Add New Task +",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskStatusColumnPreview() {
    TaskSpacesTheme {
        Column(
            modifier = Modifier
                .background(Color(0xFF21232B))
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            TaskStatusColumn(
                status = StatusVariations.PENDING,
                tasks = listOf(
                    Task(
                        title = "Design login screen",
                        tags = listOf(
                            Tag("UI", Color(0xFF2E88DD)),
                            Tag("Priority", Color(0xFFDD972E))
                        )
                    ),
                    Task(
                        title = "Connect API",
                        tags = listOf(
                            Tag("Backend", Color(0xFF26AA5D))
                        )
                    )
                ),
                onAddTaskClick = {},
            )
        }
    }
}