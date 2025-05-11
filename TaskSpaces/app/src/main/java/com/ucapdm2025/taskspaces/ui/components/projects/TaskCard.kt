package com.ucapdm2025.taskspaces.ui.components.projects

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucapdm2025.taskspaces.ui.theme.Black100
import com.ucapdm2025.taskspaces.ui.theme.PrimaryLight25
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme
import com.ucapdm2025.taskspaces.ui.theme.White100


@Composable
fun TaskCard() {
    val task = Task(
        title = "Task Title",
        tags = listOf(
            Tag("Tag 1", Color.Red),
            Tag("Tag 2", Color.Green)
        ),
    )
    Column(
        modifier = Modifier
            .background(PrimaryLight25, RoundedCornerShape(8.dp))
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = task.title,
                color = Black100,
                fontSize = 16.sp
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                task.tags.forEach { tag ->
                    Row(
                        modifier = Modifier
                            .border(1.dp, tag.color, RoundedCornerShape(8.dp))
                            .background(White100, RoundedCornerShape(8.dp))
                            .padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = tag.title,
                            color = tag.color,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

private data class Task(
    val title: String,
    val tags: List<Tag>,
)

private data class Tag(
    val title: String,
    val color: Color
)

@Preview(showBackground = true)
@Composable
fun TaskCardPreview() {
    TaskSpacesTheme {
        TaskCard()
    }
}
