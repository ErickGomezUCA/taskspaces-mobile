package com.ucapdm2025.taskspaces.ui.components.projects

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
fun TaskCard(
    title: String,
    tags: List<Tag>,
    //TODO: Replace with real navigation to the task chosen
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = PrimaryLight25),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                color = Black100,
                fontSize = 16.sp
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                tags.forEach { tag ->
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

// TODO: Replace with actual models
data class Task(
    val title: String,
    val tags: List<Tag>,
)

// TODO: Replace with actual models
data class Tag(
    val title: String,
    val color: Color
)

@Preview (showBackground = true)
@Composable
fun TaskCardPreview() {
    val tagsTest = listOf(
        Tag("Tag", Color.Red),
        Tag("Tag", Color.Blue)
    )
    TaskSpacesTheme {
        TaskCard(
            title = "Create inial mockups",
            tags = tagsTest
        )
    }
}