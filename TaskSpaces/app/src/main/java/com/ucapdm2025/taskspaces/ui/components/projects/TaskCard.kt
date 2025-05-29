package com.ucapdm2025.taskspaces.ui.components.projects

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import com.ucapdm2025.taskspaces.ui.components.general.Tag
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
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
        modifier = Modifier
            .fillMaxWidth()
            .width(220.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = ExtendedTheme.colors.primary50),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                tags.forEach { tag ->
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        tags.forEach {
                            Tag(tag = tag)
                        }
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

/**
 * A preview composable for the [TaskCard] component.
 *
 * Displays a sample task card with mock data for design-time visualization in Android Studio.
 */
@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun TaskCardPreviewLight() {
    val tagsTest = listOf(
        Tag("Tag", Color.Red),
        Tag("Tag", Color.Blue)
    )
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            TaskCard(
                title = "Create initial mockups",
                tags = tagsTest
            )
        }
    }
}

/**
 * A preview composable for the [TaskCard] component using the dark theme.
 *
 * Displays a sample task card with mock tags to visualize its appearance
 * in dark mode with a custom dark background color.
 */
@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun TaskCardPreviewDark() {
    val tagsTest = listOf(
        Tag("Tag", Color.Red),
        Tag("Tag", Color.Blue)
    )
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            TaskCard(
                title = "Create initial mockups",
                tags = tagsTest
            )
        }
    }
}