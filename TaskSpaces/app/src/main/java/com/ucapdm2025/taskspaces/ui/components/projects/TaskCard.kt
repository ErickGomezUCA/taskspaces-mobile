package com.ucapdm2025.taskspaces.ui.components.projects

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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

/**
 * A composable function that displays a task card with a title and a list of tags.
 *
 * This component is used within the project board to represent individual tasks.
 * When clicked, it should navigate to the settings or detail screen of the selected task.
 *
 * @param title The title of the task to be displayed.
 * @param tags A list of [Tag] objects representing the labels associated with the task.
 * @param onClick A lambda function triggered when the card is clicked. Intended to navigate to the task's settings screen.
 */
@Composable
fun TaskCard(
    title: String,
    tags: List<Tag>,
    //TODO: Replace with real navigation to the task chosen
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
                            .padding(horizontal = 8.dp, vertical = 4.dp),
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

/**
 * A preview composable for the [TaskCard] component.
 *
 * Displays a sample task card with mock data for design-time visualization in Android Studio.
 */
@Preview(showBackground = true)
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