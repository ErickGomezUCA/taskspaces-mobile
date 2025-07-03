package com.ucapdm2025.taskspaces.ui.components.projects

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucapdm2025.taskspaces.data.model.TagModel
import com.ucapdm2025.taskspaces.ui.components.general.Tag
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

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
    taskId: Int,
    tags: List<TagModel>,
    onDeleteClick: (Int) -> Unit,
    breadcrumb: String = "",
    onClick: () -> Unit = {},
    showDeleteButton: Boolean = false,
) {
    var showMenu by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .width(220.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = ExtendedTheme.colors.primary50),
        onClick = onClick
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (breadcrumb != "") {
                    Text(
                        text = breadcrumb,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        fontSize = 12.sp
                    )
                }

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
                        Tag(tag = tag)
                    }
                }
            }

            if (showDeleteButton) {
                IconButton(
                    onClick = { showMenu = true },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Delete") },
                    onClick = {
                        showMenu = false
                        showDeleteDialog = true
                    }
                )
            }

            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    title = { Text("Delete Task") },
                    text = { Text("Are you sure you want to delete this task?") },
                    confirmButton = {
                        TextButton(onClick = {
                            onDeleteClick(taskId)
                            showDeleteDialog = false
                        }) {
                            Text("Delete")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}


/**
 * A preview composable for the [TaskCard] component.
 *
 * Displays a sample task card with mock data for design-time visualization in Android Studio.
 */
@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun TaskCardPreviewLight() {
    val tagsTest = listOf(
        TagModel(
            id = 1,
            title = "Tag",
            color = Color.Red,
            projectId = 1,
            createdAt = "",
            updatedAt = ""
        ),
        TagModel(
            id = 2,
            title = "Tag",
            color = Color.Blue,
            projectId = 1,
            createdAt = "",
            updatedAt = ""
        )
    )
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            TaskCard(
                title = "Create initial mockups",
                tags = tagsTest,
                taskId = 1,
                onClick = {},
                onDeleteClick = {}
            )
        }
    }
}

/**
 * A preview composable for the [TaskCard] component with breadcrumb.
 *
 * Displays a sample task card with mock data and a breadcrumb for design-time visualization.
 */
@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun TaskCardBreadcrumbPreviewLight() {
    val tagsTest = listOf(
        TagModel(
            id = 1,
            title = "Tag",
            color = Color.Red,
            projectId = 1,
            createdAt = "",
            updatedAt = ""
        ),
        TagModel(
            id = 2,
            title = "Tag",
            color = Color.Blue,
            projectId = 1,
            createdAt = "",
            updatedAt = ""
        )
    )
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            TaskCard(
                taskId = 1,
                breadcrumb = "Workspace 1 / Project 1",
                title = "Create initial mockups",
                tags = tagsTest,
                onDeleteClick = {},
                onClick = {}
            )
        }
    }
}

/**
 * A preview composable for the [TaskCard] component.
 *
 * Displays a sample task card with mock data for design-time visualization in Android Studio.
 */
@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun TaskCardDeleteButtonPreviewLight() {
    val tagsTest = listOf(
        TagModel(
            id = 1,
            title = "Tag",
            color = Color.Red,
            projectId = 1,
            createdAt = "",
            updatedAt = ""
        ),
        TagModel(
            id = 2,
            title = "Tag",
            color = Color.Blue,
            projectId = 1,
            createdAt = "",
            updatedAt = ""
        )
    )
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            TaskCard(
                title = "Create initial mockups",
                tags = tagsTest,
                taskId = 1,
                onClick = {},
                onDeleteClick = {},
                showDeleteButton = true
            )
        }
    }
}

/**
 * A preview composable for the [TaskCard] component with breadcrumb.
 *
 * Displays a sample task card with mock data and a breadcrumb for design-time visualization.
 */
@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun TaskCardBreadcrumbAndDeleteButtonPreviewLight() {
    val tagsTest = listOf(
        TagModel(
            id = 1,
            title = "Tag",
            color = Color.Red,
            projectId = 1,
            createdAt = "",
            updatedAt = ""
        ),
        TagModel(
            id = 2,
            title = "Tag",
            color = Color.Blue,
            projectId = 1,
            createdAt = "",
            updatedAt = ""
        )
    )
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            TaskCard(
                taskId = 1,
                breadcrumb = "Workspace 1 / Project 1",
                title = "Create initial mockups",
                tags = tagsTest,
                onDeleteClick = {},
                onClick = {},
                showDeleteButton = true
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
        TagModel(
            id = 1,
            title = "Tag",
            color = Color.Red,
            projectId = 1,
            createdAt = "",
            updatedAt = ""
        ),
        TagModel(
            id = 2,
            title = "Tag",
            color = Color.Blue,
            projectId = 1,
            createdAt = "",
            updatedAt = ""
        )
    )
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            TaskCard(
                title = "Create initial mockups",
                tags = tagsTest,
                taskId = 2,
                onClick = {},
                onDeleteClick = {}
            )
        }
    }
}

/**
 * A preview composable for the [TaskCard] component using the dark theme with breadcrumb.
 */
@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun TaskCardBreadcrumbPreviewDark() {
    val tagsTest = listOf(
        TagModel(
            id = 1,
            title = "Tag",
            color = Color.Red,
            projectId = 1,
            createdAt = "",
            updatedAt = ""
        ),
        TagModel(
            id = 2,
            title = "Tag",
            color = Color.Blue,
            projectId = 1,
            createdAt = "",
            updatedAt = ""
        )
    )
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            TaskCard(
                taskId = 1,
                breadcrumb = "Workspace 1 / Project 1",
                title = "Create initial mockups",
                tags = tagsTest,
                onDeleteClick = {},
                onClick = {}
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
fun TaskCardDeleteButtonPreviewDark() {
    val tagsTest = listOf(
        TagModel(
            id = 1,
            title = "Tag",
            color = Color.Red,
            projectId = 1,
            createdAt = "",
            updatedAt = ""
        ),
        TagModel(
            id = 2,
            title = "Tag",
            color = Color.Blue,
            projectId = 1,
            createdAt = "",
            updatedAt = ""
        )
    )
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            TaskCard(
                title = "Create initial mockups",
                tags = tagsTest,
                taskId = 2,
                onClick = {},
                onDeleteClick = {},
                showDeleteButton = true
            )
        }
    }
}

/**
 * A preview composable for the [TaskCard] component using the dark theme with breadcrumb.
 */
@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun TaskCardBreadcrumbAndDeleteButtonPreviewDark() {
    val tagsTest = listOf(
        TagModel(
            id = 1,
            title = "Tag",
            color = Color.Red,
            projectId = 1,
            createdAt = "",
            updatedAt = ""
        ),
        TagModel(
            id = 2,
            title = "Tag",
            color = Color.Blue,
            projectId = 1,
            createdAt = "",
            updatedAt = ""
        )
    )
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            TaskCard(
                taskId = 1,
                breadcrumb = "Workspace 1 / Project 1",
                title = "Create initial mockups",
                tags = tagsTest,
                onDeleteClick = {},
                onClick = {},
                showDeleteButton = true
            )
        }
    }
}