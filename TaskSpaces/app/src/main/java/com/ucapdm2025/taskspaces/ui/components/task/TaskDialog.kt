package com.ucapdm2025.taskspaces.ui.components.task

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.Textsms
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucapdm2025.taskspaces.TaskSpacesApplication
import com.ucapdm2025.taskspaces.ui.components.general.DropdownMenu
import com.ucapdm2025.taskspaces.ui.components.general.DropdownMenuOption
import com.ucapdm2025.taskspaces.ui.components.general.FeedbackIcon
import com.ucapdm2025.taskspaces.ui.components.projects.StatusVariations
import com.ucapdm2025.taskspaces.ui.screens.task.TaskViewModel
import com.ucapdm2025.taskspaces.ui.screens.task.TaskViewModelFactory
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme


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
 * @param taskId: The ID of the task to be displayed in the dialog.
 * @param onDismissRequest: Callback function to be invoked when the dialog is dismissed.
 */
@Composable
fun TaskDialog(
    taskId: Int,
    onDismissRequest: () -> Unit,
) {
    val application = LocalContext.current.applicationContext as TaskSpacesApplication
    val taskRepository = application.appProvider.provideTaskRepository()
    val bookmarkRepository = application.appProvider.provideBookmarkRepository()
    val viewModel: TaskViewModel =
        viewModel(factory = TaskViewModelFactory(taskId, taskRepository, bookmarkRepository))

    val task = viewModel.task.collectAsStateWithLifecycle()
    val isBookmarked = viewModel.isBookmarked.collectAsStateWithLifecycle()

//    Change task id on dialog load
    LaunchedEffect(taskId) {
        viewModel.loadTask(taskId)
    }

    //    Remove saved task id and then hide the dialog
    fun hideDialog() {
        viewModel.clearTask()
        onDismissRequest()
    }

    AlertDialog(
        onDismissRequest = { hideDialog() },
        containerColor = MaterialTheme.colorScheme.background,
//        Cancel and save buttons
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = { hideDialog() },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ) { Text(text = "Cancel") }

                Button(
                    onClick = {
                        viewModel.updateTask(
                            id = task.value?.id ?: 0,
                            title = task.value?.title ?: "",
                            description = task.value?.description ?: "",
                            deadline = task.value?.deadline ?: "",
                            timer = task.value?.timer ?: 0f,
                            status = task.value?.status ?: StatusVariations.PENDING
                        )
                        hideDialog()
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                ) { Text(text = "Save") }
            }
        },
        text = {
            if (true) {
                AlertDialog(
                    onDismissRequest = { hideDialog() },
                    title = { Text("Task not found") },
                    text = { Text("The task with ID $taskId could not be found.") },
                    confirmButton = {
                        Button(onClick = { hideDialog() }) {
                            Text("OK")
                        }
                    }
                )
            }

//            Show feedback icon if task is not found
//            TODO: Add loading and error states
            if (task.value == null) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    FeedbackIcon(
                        icon = Icons.Default.Close,
                        title = "Sorry, we couldn't find this task.",
                    )
                }

            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    //BREADCRUMB AND DROPDOWN MENU
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                task.value?.breadcrumb ?: "/",
                                fontSize = 14.sp,
                                color = ExtendedTheme.colors.background50
                            )
                        }

                        DropdownMenu(
                            options = listOf(
                                DropdownMenuOption(
                                    label = if (!isBookmarked.value) "Bookmark" else "Remove Bookmark",
                                    icon = {
                                        if (!isBookmarked.value) {
                                            Icon(
                                                Icons.Default.BookmarkBorder,
                                                contentDescription = "Bookmark",
                                                tint = MaterialTheme.colorScheme.onBackground
                                            )
                                        } else {
                                            Icon(
                                                Icons.Default.BookmarkRemove,
                                                contentDescription = "Remove Bookmark",
                                                tint = MaterialTheme.colorScheme.onBackground
                                            )
                                        }
                                    },
                                    onClick = {
                                        if (!isBookmarked.value) {
                                            viewModel.bookmarkTask()
                                        } else {
                                            viewModel.removeBookmarkTask()
                                        }
                                    }
                                ),
                            ),
                        )
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

                            OutlinedTextField(
                                value = task.value?.title ?: "No title",
                                onValueChange = { viewModel.setTaskData(title = it) },
                                placeholder = { Text("Add a title...") },
                                modifier = Modifier.fillMaxWidth()
                            )
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

                            SelectStatusDropdown(
                                value = task.value?.status ?: StatusVariations.PENDING
                            ) { status ->
                                viewModel.setTaskData(status = status)
                            }
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
                        OutlinedTextField(
                            value = task.value?.description ?: "",
                            onValueChange = { viewModel.setTaskData(description = it) },
                            placeholder = { Text("Add a description...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
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
//                            TODO: Implement tags
//                            task.value?.tags?.forEach { tag ->
//                                Tag(tag = tag)
//                            }
                        }
                        OutlinedButton(
                            onClick = { /*TODO*/ },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                        ) {
                            Text("Manage tags")
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
//            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                task.value?.images.forEach {
//                    Image(
//                        painter = painterResource(id = it),
//                        contentDescription = null,
//                        modifier = Modifier.size(48.dp)
//                    )
//                }
//            }
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
//                            val formatter = DateTimeFormatter.ofPattern("d/MMM/yyyy - hh:mm a")
//                            val dateText = task.value?.deadline?.format(formatter)
//                            val weeksLeft =
//                                java.time.Duration.between(
//                                    LocalDateTime.now(),
//                                    task.value?.deadline
//                                ).toDays() / 7
//                            Row(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .border(
//                                        1.dp,
//                                        MaterialTheme.colorScheme.onBackground,
//                                        RoundedCornerShape(8.dp)
//                                    ),
//                                horizontalArrangement = Arrangement.Center
//                            ) {
//                                Text(
//                                    dateText ?: "",
//                                    fontWeight = FontWeight.Medium,
//                                    modifier = Modifier.padding(4.dp),
//                                    color = MaterialTheme.colorScheme.onBackground
//                                )
//                            }
//                            Text(
//                                "$weeksLeft weeks left until deadline",
//                                color = ExtendedTheme.colors.background50,
//                                fontSize = 12.sp
//                            )
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
//            if (task.value?.userTimer == null) {
//                OutlinedButton(
//                    onClick = { /*TODO*/ },
//                    modifier = Modifier.fillMaxWidth(),
//                    shape = RoundedCornerShape(8.dp),
//                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
//                ) {
//                    Text("+ Add Timer")
//                }
//            } else {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .border(
//                            1.dp,
//                            MaterialTheme.colorScheme.onBackground,
//                            RoundedCornerShape(8.dp)
//                        ),
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    Text(
//                        text = task.value?.userTimer.time,
//                        fontSize = 18.sp,
//                        color = MaterialTheme.colorScheme.onBackground,
//                        modifier = Modifier.padding(4.dp)
//                    )
//                }
//                Spacer(modifier = Modifier.width(8.dp))
//                OutlinedButton(
//                    onClick = { /* TODO: Pause, resume */ },
//                    modifier = Modifier.fillMaxWidth(),
//                    shape = RoundedCornerShape(8.dp),
//                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
//                ) {
//                    Text(if (task.value?.userTimer.isRunning) "Pause" else "Resume")
//                }
//            }
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
//                            TODO: Implement members
//                            task.value?.assignedMembers?.forEach { user ->
//                                Image(
//                                    painter = painterResource(id = android.R.drawable.ic_menu_camera),
//                                    contentDescription = user.fullname,
//                                    modifier = Modifier
//                                        .size(36.dp)
//                                        .background(ExtendedTheme.colors.primary50, CircleShape)
//                                )
//                            }
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
//                            TODO: Implement comments
//                            task.value?.comments?.forEach { comment ->
//                                val user = usersDummies.find { it.id == comment.authorId }
//                                Row(
//                                    verticalAlignment = Alignment.Top,
//                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
//                                    modifier = Modifier.fillMaxWidth()
//                                ) {
//                                    Image(
//                                        painter = painterResource(id = android.R.drawable.ic_menu_camera),
//                                        contentDescription = user?.username ?: "User",
//                                        modifier = Modifier
//                                            .size(36.dp)
//                                            .background(ExtendedTheme.colors.primary50, CircleShape)
//                                    )
//                                    Column {
//                                        Row {
//                                            Text(
//                                                text = user?.username ?: "Unknown",
//                                                fontWeight = FontWeight.Bold,
//                                                color = MaterialTheme.colorScheme.onBackground
//                                            )
//                                            Spacer(modifier = Modifier.width(8.dp))
//                                            Text(
//                                                text = comment.createdAt,
//                                                color = MaterialTheme.colorScheme.onBackground,
//                                                fontSize = 12.sp
//                                            )
//                                        }
//                                        Spacer(modifier = Modifier.height(2.dp))
//                                        Text(
//                                            text = comment.content,
//                                            color = MaterialTheme.colorScheme.onBackground
//                                        )
//                                    }
//                                }
//                            }
                        }
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.spacedBy(16.dp)
//                        ) {
//                            OutlinedButton(
//                                onClick = { /*TODO*/ },
//                                modifier = Modifier.weight(1f),
//                                shape = RoundedCornerShape(8.dp),
//                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
//                            ) {
//                                Text("Cancel")
//                            }
//                            Button(
//                                onClick = { },
//                                modifier = Modifier.weight(1f),
//                                shape = RoundedCornerShape(8.dp),
//                            ) {
//                                Text("Save")
//                            }
//                        }
                    }
                }

            }
        }
    )
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
            TaskDialog(taskId = 1, onDismissRequest = {})
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
            TaskDialog(taskId = 1, onDismissRequest = {})
        }
    }
}
