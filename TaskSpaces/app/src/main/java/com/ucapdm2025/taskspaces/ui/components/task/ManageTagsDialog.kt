package com.ucapdm2025.taskspaces.ui.components.task

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import com.ucapdm2025.taskspaces.data.model.TagModel
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

//TODO: Improve UI, specially the dialog width and select role dropdown
/**
 * Displays a dialog for managing tags, allowing users to edit, add, or delete tags.
 *
 * @param tags List of existing tags to display and manage.
 * @param onDismissRequest Callback invoked when the dialog is dismissed.
 * @param onAddTag Callback invoked when a new tag is added.
 * @param onUpdateTag Callback invoked when an existing tag is updated.
 * @param onDeleteTag Callback invoked when a tag is deleted.
 * @param onAssignTag Callback invoked when a tag is assigned.
 * @param onUnassignTag Callback invoked when a tag is unassigned.
 */
@Composable
fun ManageTagsDialog(
    tags: List<TagModel> = emptyList(),
    assignedTags: List<TagModel> = emptyList(),
    onDismissRequest: () -> Unit = {},
    onAddTag: (title: String, color: Color) -> Unit = { title, color -> },
    onUpdateTag: (id: Int, title: String, color: Color) -> Unit = { id, title, color -> },
    onDeleteTag: (id: Int) -> Unit = { id -> },
    onAssignTag: (tagId: Int) -> Unit = { tagId -> },
    onUnassignTag: (tagId: Int) -> Unit = { tagId -> }
) {
    var newTitle by remember { mutableStateOf("") }
    var newColor by remember { mutableStateOf(Color(0xFF81C784)) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Manage Tags") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Existing tags
                tags.forEach { tag ->
                    var title by remember { mutableStateOf(tag.title) }
                    var color by remember { mutableStateOf(tag.color) }
                    val checked = assignedTags.any { it.id == tag.id }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = checked,
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    onAssignTag(tag.id)
                                } else {
                                    onUnassignTag(tag.id)
                                }
                            }
                        )
                        OutlinedTextField(
                            value = title,
                            onValueChange = {
                                title = it
                                onUpdateTag(tag.id, it, color)
                            },
                            label = { Text("Title") },
                            modifier = Modifier.weight(1f)
                        )
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(color, CircleShape)
                                .clickable {
                                    // Cycle through a predefined palette
                                    val palette = listOf(
                                        Color.Red,
                                        Color.Green,
                                        Color.Blue,
                                        Color.Magenta,
                                        Color.Yellow
                                    )
                                    val nextColor =
                                        palette[(palette.indexOf(color) + 1) % palette.size]
                                    color = nextColor
                                    onUpdateTag(tag.id, title, color)
                                }
                        )
                        IconButton(onClick = { onDeleteTag(tag.id) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Tag"
                            )
                        }
                    }
                }

                Divider(modifier = Modifier.padding(vertical = 16.dp))

                // Add new tag
                Text("Add New Tag:")
                OutlinedTextField(
                    value = newTitle,
                    onValueChange = { newTitle = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(newColor, CircleShape)
                            .clickable {
                                val palette = listOf(
                                    Color.Red,
                                    Color.Green,
                                    Color.Blue,
                                    Color.Magenta,
                                    Color.Yellow
                                )
                                newColor = palette[(palette.indexOf(newColor) + 1) % palette.size]
                            }
                    )
                    Text("Pick color")
                }

                TextButton(
                    onClick = {
                        if (newTitle.isNotBlank()) {
                            onAddTag(newTitle.trim(), newColor)
                            newTitle = ""
                            newColor = Color(0xFF81C784)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Tag +")
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismissRequest, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp)) {
                Text("Close")
            }
        }
    )
}

/**
 * Preview of ManageTagsDialog with sample tags in light theme.
 */
@Preview(showBackground = true)
@Composable
fun ManageMembersDialogLightPreview() {
    val tags = listOf(
        TagModel(
            id = 1,
            title = "Tag 1",
            color = Color(0xFF81C784),
            projectId = 1
        ),
        TagModel(
            id = 2,
            title = "Tag 2",
            color = Color(0xFF64B5F6),
            projectId = 1
        ),
        TagModel(
            id = 3,
            title = "Tag 3",
            color = Color(0xFFFF8A65),
            projectId = 1
        )
    )

    val assignedTags = listOf(
        TagModel(
            id = 1,
            title = "Assigned Tag 1",
            color = Color(0xFF81C784),
            projectId = 1
        ),
        TagModel(
            id = 2,
            title = "Assigned Tag 2",
            color = Color(0xFF64B5F6),
            projectId = 1
        )
    )

    TaskSpacesTheme {
        ExtendedColors {
            ManageTagsDialog(tags = tags, assignedTags = assignedTags)
        }
    }
}

/**
 * Preview of ManageTagsDialog with no tags in light theme.
 */
@Preview(showBackground = true)
@Composable
fun ManageMembersDialogEmptyLightPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            ManageTagsDialog()
        }
    }
}

/**
 * Preview of ManageTagsDialog with sample tags in dark theme.
 */
@Preview(showBackground = true)
@Composable
fun ManageMembersDialogDarkPreview() {
    val tags = listOf(
        TagModel(
            id = 1,
            title = "Tag 1",
            color = Color(0xFF81C784),
            projectId = 1
        ),
        TagModel(
            id = 2,
            title = "Tag 2",
            color = Color(0xFF64B5F6),
            projectId = 1
        ),
        TagModel(
            id = 3,
            title = "Tag 3",
            color = Color(0xFFFF8A65),
            projectId = 1
        )
    )
    
    val assignedTags = listOf(
        TagModel(
            id = 1,
            title = "Assigned Tag 1",
            color = Color(0xFF81C784),
            projectId = 1
        ),
        TagModel(
            id = 2,
            title = "Assigned Tag 2",
            color = Color(0xFF64B5F6),
            projectId = 1
        )
    )


    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            ManageTagsDialog(tags = tags, assignedTags = assignedTags)
        }
    }
}

/**
 * Preview of ManageTagsDialog with no tags in dark theme.
 */
@Preview(showBackground = true)
@Composable
fun ManageMembersDialogEmptyDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            ManageTagsDialog()
        }
    }
}
