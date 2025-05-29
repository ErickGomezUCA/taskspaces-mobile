package com.ucapdm2025.taskspaces.ui.components.task

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.Textsms
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucapdm2025.taskspaces.R
import com.ucapdm2025.taskspaces.ui.components.general.Tag
import com.ucapdm2025.taskspaces.ui.components.projects.StatusVariations
import com.ucapdm2025.taskspaces.ui.components.projects.TaskStatus
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Data class representing a user in the system.
 *
 * @property id Unique identifier of the user.
 * @property name Display name of the user.
 * @property avatarResId Resource ID for the user's avatar image.
 */
data class User(
    val id: Int,
    val name: String,
    val avatarResId: Int
)

/**
 * Data class representing a timer associated with a user.
 *
 * @property time The current time value of the timer, formatted as a string (e.g., "12:34").
 * @property isRunning Indicates whether the timer is currently active.
 */
data class UserTimer(
    val time: String,
    val isRunning: Boolean
)

/**
 * Data class representing a comment made on a task.
 *
 * @property userId ID of the user who made the comment.
 * @property time Timestamp or relative time when the comment was posted (e.g., "2h ago").
 * @property content The textual content of the comment.
 */
data class Comment(
    val userId: Int,
    val time: String,
    val content: String
)

/**
 * Data class representing a task within a project.
 *
 * @property breadcrumb A string representing the task's location or category path (e.g., "Marketing / Landing Page").
 * @property title The title or name of the task.
 * @property status The current status of the task, represented by [StatusVariations].
 * @property description A detailed description of the task.
 * @property tags A list of [Tag] objects used to label or categorize the task.
 * @property images A list of drawable resource IDs representing images attached to the task.
 * @property deadline The deadline for the task, represented as a [LocalDateTime].
 * @property userTimer Optional [UserTimer] object tracking time spent on the task.
 * @property assignedMembersIds A list of user IDs assigned to the task.
 * @property comments A list of [Comment] objects associated with the task.
 */
data class Task(
    val breadcrumb: String,
    val title: String,
    val status: StatusVariations,
    val description: String,
    val tags: List<Tag>,
    val images: List<Int>,
    val deadline: LocalDateTime,
    val userTimer: UserTimer?,
    val assignedMembersIds: List<Int>,
    val comments: List<Comment>
)

/**
 * A list of dummy [User] objects used for testing or preview purposes.
 *
 * Each user includes a unique ID, name, and a placeholder avatar resource.
 */
val dummyUsers = listOf(
    User(id = 1, name = "Laura", avatarResId = android.R.drawable.ic_menu_camera),
    User(id = 2, name = "Marco", avatarResId = android.R.drawable.ic_menu_camera),
    User(id = 3, name = "Juan", avatarResId = android.R.drawable.ic_menu_camera)
)

/**
 * A dummy [Task] object used for testing or preview purposes.
 *
 * This task includes sample data such as title, description, tags, deadline,
 * assigned users, and comments to simulate a real task in the UI.
 */
val dummyTask = Task(
    breadcrumb = "Marketing / Landing Page",
    title = "Design Hero Section",
    status = StatusVariations.PENDING,
    description = "Create an engaging hero section for the new landing page including images and a catchy headline.",
    tags = listOf(Tag("UI", Color(0xFF2E88DD)), Tag("Urgent", Color(0xFFDD2E2E))),
    images = listOf(/* TODO: Replace with drawable resource ids */),
    deadline = LocalDateTime.of(2025, 12, 7, 22, 0),
    userTimer = UserTimer(time = "12:34", isRunning = true),
    assignedMembersIds = listOf(1, 2),
    comments = listOf(
        Comment(userId = 1, time = "2h ago", content = "Looks great so far!"),
        Comment(userId = 2, time = "1h ago", content = "I'll handle the animation part.")
    )
)


/**
 * Composable that displays a detailed dialog for a given [Task].
 *
 * The dialog includes:
 * - Breadcrumb navigation for context.
 * - Task title and status.
 * - Editable description field.
 * - List of tags with an option to add more.
 * - Media/image attachments with an add button.
 * - Deadline display with time left and add option.
 * - Timer section to show or add a timer.
 * - Assigned members with avatars and add member button.
 * - Comments section showing user avatars, names, times, and content.
 * - Action buttons for cancel and save.
 *
 * @param task The [Task] to display and edit in the dialog.
 */
@Composable
fun TaskDialog(task: Task) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(16.dp))
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        //BREADCRUMB
        Column {
            Text(task.breadcrumb, fontSize = 14.sp, color = ExtendedTheme.colors.background50)
        }


        //TITLE
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Task,
                    contentDescription = "Task",
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(task.title, fontSize = 33.sp, color = MaterialTheme.colorScheme.onBackground)
            }
        }

        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Flag,
                    contentDescription = "Status",
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(8.dp))
                TaskStatus(status = task.status)
            }
        }


        //DESCRIPTION
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Description,
                    contentDescription = "Description",
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    "Description",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            TextField(
                value = task.description,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth()
            )
        }

        //TAGS
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Tag,
                    contentDescription = "Tags",
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Tags",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                task.tags.forEach { tag ->
                    Tag(tag = tag)
                }
            }
            OutlinedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                Text("+ Add tags")
            }
        }

        //MEDIA
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Image,
                    contentDescription = "Media",
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Media",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                task.images.forEach {
                    Image(
                        painter = painterResource(id = it),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
            OutlinedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                Text("+ Add Media")
            }
        }

        //DEADLINE
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.CalendarMonth,
                    contentDescription = "Deadline",
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Deadline",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
            Column {
                val formatter = DateTimeFormatter.ofPattern("d/MMM/yyyy - hh:mm a")
                val dateText = task.deadline.format(formatter)
                val weeksLeft =
                    java.time.Duration.between(LocalDateTime.now(), task.deadline).toDays() / 7
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onBackground,
                            RoundedCornerShape(8.dp)
                        ),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        dateText,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(4.dp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Text(
                    "$weeksLeft weeks left until deadline",
                    color = ExtendedTheme.colors.background50,
                    fontSize = 12.sp
                )
            }
            OutlinedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                Text("+ Add Deadline")
            }
        }

        //TIMERS
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Timer,
                    contentDescription = "Timer",
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Timer",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            if (task.userTimer == null) {
                OutlinedButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Text("+ Add Timer")
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onBackground,
                            RoundedCornerShape(8.dp)
                        ),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = task.userTimer.time,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(4.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedButton(
                    onClick = { /* TODO: Pause, resume */ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Text(if (task.userTimer.isRunning) "Pause" else "Resume")
                }
            }
        }

        //MEMBERS
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Members",
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Members",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                task.assignedMembersIds.forEach { memberId ->
                    val user = dummyUsers.find { it.id == memberId }
                    val avatarRes = user?.avatarResId ?: android.R.drawable.ic_menu_help

                    Image(
                        painter = painterResource(id = avatarRes),
                        contentDescription = user?.name ?: "Unknown user",
                        modifier = Modifier
                            .size(36.dp)
                            .background(ExtendedTheme.colors.primary50, CircleShape)
                    )
                }
            }
            OutlinedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                Text("+ Add member")
            }
        }

        //COMMENTS
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Textsms,
                    contentDescription = "Comments",
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Comments",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                task.comments.forEach { comment ->
                    val user = dummyUsers.find { it.id == comment.userId }
                    val avatarRes = user?.avatarResId ?: android.R.drawable.ic_menu_help

                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = avatarRes),
                            contentDescription = user?.name ?: "User",
                            modifier = Modifier
                                .size(36.dp)
                                .background(ExtendedTheme.colors.primary50, CircleShape)
                        )
                        Column {
                            Row {
                                Text(
                                    text = user?.name ?: "Unknown",
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = comment.time,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontSize = 12.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = comment.content,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Text("Cancel")
                }
                Button(
                    onClick = { },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text("Save")
                }
            }
        }
    }
}

/**
 * A preview composable for the [TaskDialog] component using the light theme.
 *
 * This preview allows developers to visualize how the task dialog appears
 * in light mode with system UI and background enabled.
 */
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TaskDialogPreviewLight() {
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            TaskDialog(task = dummyTask)
        }
    }
}

/**
 * A preview composable for the [TaskDialog] component using the dark theme.
 *
 * This preview allows developers to visualize how the task dialog appears
 * in dark mode with system UI and background enabled.
 */
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TaskDialogPreviewDark() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            TaskDialog(task = dummyTask)
        }
    }
}
