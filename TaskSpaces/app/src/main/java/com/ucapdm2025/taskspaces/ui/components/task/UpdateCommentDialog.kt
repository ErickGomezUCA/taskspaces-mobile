package com.ucapdm2025.taskspaces.ui.components.task

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ucapdm2025.taskspaces.data.model.CommentModel
import com.ucapdm2025.taskspaces.data.model.UserModel
import com.ucapdm2025.taskspaces.ui.components.general.UserAvatar
import com.ucapdm2025.taskspaces.ui.components.projects.StatusVariations
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme
import com.ucapdm2025.taskspaces.utils.formatRelativeDate
import com.ucapdm2025.taskspaces.utils.toLocalDateTime
import java.time.LocalDateTime

/**
 * UpdateCommentDialog is a composable function that displays a dialog for editing a comment.
 *
 * @param comment The comment to be edited.
 * @param onDismissRequest Callback to be invoked when the dialog is dismissed.
 * @param onUpdateComment Callback to be invoked with the new content when the comment is updated.
 */
@Composable
fun UpdateCommentDialog(
    comment: CommentModel,
    onDismissRequest: () -> Unit,
    onUpdateComment: (newContent: String) -> Unit,
) {
    var newContent = remember { mutableStateOf(comment.content) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Edit Comment") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                // Author info
                Row(verticalAlignment = Alignment.CenterVertically) {
                    UserAvatar(avatar = comment.author.avatar, size = 36)
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text(
                            text = comment.author.username,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = formatRelativeDate(
                                    comment.createdAt?.toLocalDateTime() ?: LocalDateTime.now()
                                ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = MaterialTheme.typography.bodySmall.fontSize
                            )
                            if (comment.edited) {
                                Spacer(Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edited",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(Modifier.width(2.dp))
                                Text(
                                    text = "(edited)",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = MaterialTheme.typography.bodySmall.fontSize
                                )
                            }
                        }
                    }
                }
                // Editable text field
                OutlinedTextField(
                    value = newContent.value,
                    onValueChange = { newContent.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Edit your comment...") },
                    singleLine = false,
                    shape = RoundedCornerShape(8.dp)
                )
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = { onDismissRequest() },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ) { Text(text = "Cancel") }

                Button(
                    onClick = {
                        onUpdateComment(newContent.value)
                        onDismissRequest()
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    enabled = newContent.value.isNotBlank()
                ) { Text(text = "Edit") }
            }
        },
    )
}

/**
 * Previews for UpdateCommentDialog composable function.
 * These previews show how the dialog looks with light and dark themes.
 */
@Preview(showBackground = true)
@Composable
fun UpdateCommentDialogLightPreview() {
    val comment = CommentModel(
        id = 1,
        content = "This is a sample comment.",
        authorId = 1,
        author = UserModel(
            id = 1,
            username = "john_doe",
            fullname = "John Doe",
            email = "johndoe@domain.com",
            avatar = "https://example.com/avatar.jpg"
        ),
        taskId = 1,
        edited = false,
        createdAt = "2023-10-01T12:00:00Z",
        updatedAt = null
    )

    TaskSpacesTheme {
        ExtendedColors {
            UpdateCommentDialog(
                comment, onDismissRequest = {}, onUpdateComment = {}
            )
        }
    }
}

/**
 * Preview for UpdateCommentDialog in dark theme.
 */
@Preview(showBackground = true)
@Composable
fun UpdateCommentDialogDarkPreview() {
    val comment = CommentModel(
        id = 1,
        content = "This is a sample comment.",
        authorId = 1,
        author = UserModel(
            id = 1,
            username = "john_doe",
            fullname = "John Doe",
            email = "johndoe@domain.com",
            avatar = "https://example.com/avatar.jpg"
        ),
        taskId = 1,
        edited = false,
        createdAt = "2023-10-01T12:00:00Z",
        updatedAt = null
    )

    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            UpdateCommentDialog(
                comment, onDismissRequest = {}, onUpdateComment = {}
            )
        }
    }
}

