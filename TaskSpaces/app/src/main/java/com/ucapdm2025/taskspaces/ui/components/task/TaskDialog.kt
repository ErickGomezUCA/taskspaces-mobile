package com.ucapdm2025.taskspaces.ui.components.task

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.Textsms
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.rememberAsyncImagePainter
import com.ucapdm2025.taskspaces.TaskSpacesApplication
import com.ucapdm2025.taskspaces.data.model.TagModel
import com.ucapdm2025.taskspaces.helpers.UiState
import com.ucapdm2025.taskspaces.ui.components.general.DropdownMenu
import com.ucapdm2025.taskspaces.ui.components.general.DropdownMenuOption
import com.ucapdm2025.taskspaces.ui.components.general.FeedbackIcon
import com.ucapdm2025.taskspaces.ui.components.general.Tag
import com.ucapdm2025.taskspaces.ui.components.general.UserAvatar
import com.ucapdm2025.taskspaces.ui.components.projects.StatusVariations
import com.ucapdm2025.taskspaces.ui.components.workspace.MemberRoles
import com.ucapdm2025.taskspaces.ui.screens.project.ProjectViewModel
import com.ucapdm2025.taskspaces.ui.screens.task.TaskViewModel
import com.ucapdm2025.taskspaces.ui.screens.task.TaskViewModelFactory
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme
import com.ucapdm2025.taskspaces.utils.formatRelativeDate
import com.ucapdm2025.taskspaces.utils.toLocalDateTime
import java.time.LocalDateTime

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
    projectViewModel: ProjectViewModel
) {
    val application = LocalContext.current.applicationContext as TaskSpacesApplication
    val taskRepository = application.appProvider.provideTaskRepository()
    val tagRepository = application.appProvider.provideTagRepository()
    val memberRoleRepository = application.appProvider.provideMemberRoleRepository()
    val bookmarkRepository = application.appProvider.provideBookmarkRepository()
    val commentRepository = application.appProvider.provideCommentRepository()
    val viewModel: TaskViewModel =
        viewModel(
            factory = TaskViewModelFactory(
                taskId = taskId,
                taskRepository = taskRepository,
                tagRepository = tagRepository,
                memberRoleRepository = memberRoleRepository,
                bookmarkRepository = bookmarkRepository,
                commentRepository = commentRepository
            )
        )

    val task = viewModel.task.collectAsStateWithLifecycle()
    val taskState = viewModel.taskState.collectAsStateWithLifecycle()
    val tags = viewModel.tags.collectAsStateWithLifecycle()
    val projectTags = viewModel.projectTags.collectAsStateWithLifecycle()
    val isBookmarked = viewModel.isBookmarked.collectAsStateWithLifecycle()
    val showTagsDialog = viewModel.showTagsDialog.collectAsStateWithLifecycle()
    val showTaskMembersDialog = viewModel.showTaskMembersDialog.collectAsStateWithLifecycle()
    val members = viewModel.members.collectAsStateWithLifecycle()
    val workspaceMembers = viewModel.workspaceMembers.collectAsStateWithLifecycle()
    val comments = viewModel.comments.collectAsStateWithLifecycle()
    val newComment = viewModel.newComment.collectAsStateWithLifecycle()
    val showUpdateCommentDialog = viewModel.showUpdateCommentDialog.collectAsStateWithLifecycle()
    val selectedCommentToUpdate = viewModel.selectedCommentToUpdate.collectAsStateWithLifecycle()
    val selectedMediaUris = remember { mutableStateListOf<Uri>() }

    val pickMediaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments(),
        onResult = { uris ->
            selectedMediaUris.clear()
            selectedMediaUris.addAll(uris)
        }
    )


    val hasPermission = projectViewModel.hasSufficientPermissions(MemberRoles.COLLABORATOR)

//    Change task id on dialog load
    LaunchedEffect(taskId) {
        viewModel.loadTask(taskId)
    }

//    Log when tags value changes
    LaunchedEffect(tags.value) {
        Log.d("TaskDialog", "Tags changed: ${tags.value}")
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

                if (hasPermission) {
                    Button(
                        onClick = {
                            viewModel.updateTask(
                                id = task.value?.id ?: 0,
                                title = task.value?.title ?: "",
                                description = task.value?.description ?: "",
                                deadline = task.value?.deadline,
                                timer = task.value?.timer ?: 0f,
                                status = task.value?.status ?: StatusVariations.PENDING
                            )
                            hideDialog()
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                    ) { Text(text = "Save") }
                }
            }
        },
        text = {
            Column {
//            Tags dialog
                if (showTagsDialog.value) {
                    ManageTagsDialog(
                        tags = projectTags.value,
                        assignedTags = tags.value,
                        onDismissRequest = { viewModel.hideTagsDialog() },
                        onAddTag = { title, color ->
                            viewModel.addTag(title, color)
                        },
                        onUpdateTag = { id, title, color ->
                            viewModel.updateTag(id, title, color)
                        },
                        onDeleteTag = { id ->
                            viewModel.deleteTag(id)
                        },
                        onAssignTag = { id ->
                            viewModel.assignTagToTask(id)
                        },
                        onUnassignTag = { id ->
                            viewModel.unassignTagFromTask(id)
                        }
                    )
                }

//            Members dialog
                if (showTaskMembersDialog.value) {
                    ManageTaskMembersDialog(
                        assignedMembers = members.value,
                        workspaceMembers = workspaceMembers.value,
                        onDismissRequest = { viewModel.hideTaskMembersDialog() },
                        onAssignMember = { id ->
                            viewModel.assignMemberToTask(id)
                        },
                        onUnassignMember = { id ->
                            viewModel.unassignMemberFromTask(id)
                        }
                    )
                }

//            Update comment dialog
                if (showUpdateCommentDialog.value) {
                    UpdateCommentDialog(
                        comment = selectedCommentToUpdate.value!!,
                        onDismissRequest = { viewModel.hideUpdateCommentDialog() },
                        onUpdateComment = { newContent ->
                            viewModel.updateComment(
                                id = selectedCommentToUpdate.value!!.id,
                                content = newContent
                            )
                        }
                    )
                }

//            Show feedback icon if task is not found
                when (val state = taskState.value) {
                    UiState.Loading -> Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) { CircularProgressIndicator() }

                    is UiState.Error -> Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {}

                    is UiState.Success -> {
                        val loadedTask = state.data
                        if (loadedTask == null) {
                            FeedbackIcon(
                                icon = Icons.Default.Close,
                                title = "Sorry, we couldn't find this task."
                            )
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
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = task.value?.breadcrumb ?: "/",
                                            fontSize = 14.sp,
                                            color = ExtendedTheme.colors.background50,
                                            maxLines = 1,
                                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }

                                    if (hasPermission) {
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
                                                        if (!isBookmarked.value)
                                                            viewModel.bookmarkTask()
                                                        else
                                                            viewModel.removeBookmarkTask()
                                                    }
                                                ),
                                            ),
                                        )
                                    }
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
                                        tags.value.forEach {
                                            Tag(it)
                                        }
                                    }
                                    OutlinedButton(
                                        onClick = { viewModel.showTagsDialog() },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(8.dp),
                                        border = BorderStroke(
                                            1.dp,
                                            MaterialTheme.colorScheme.primary
                                        )
                                    ) {
                                        Text(if (tags.value != emptyList<TagModel>()) "Manage Tags" else "Add Tags +")
                                    }
                                }

                                //MEDIA
//                    TODO: Implement media upload and display
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
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

                                    OutlinedButton(
                                        onClick = {
                                            pickMediaLauncher.launch(arrayOf("image/*", "video/*"))
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(8.dp),
                                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                                    ) {
                                        Text("Add Media +")
                                    }

                                    if (selectedMediaUris.isNotEmpty()) {
                                        LazyRow(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(top = 8.dp),
                                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                                        ) {
                                            items(selectedMediaUris) { uri ->
                                                val context = LocalContext.current
                                                val mimeType = context.contentResolver.getType(uri)
                                                Box(
                                                    modifier = Modifier
                                                        .size(80.dp)
                                                        .clip(RoundedCornerShape(8.dp))
                                                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                                                ) {
                                                    if (mimeType?.startsWith("image") == true) {
                                                        Image(
                                                            painter = rememberAsyncImagePainter(uri),
                                                            contentDescription = "Image",
                                                            modifier = Modifier.fillMaxSize()
                                                        )
                                                    } else if (mimeType?.startsWith("video") == true) {
                                                        Box(
                                                            contentAlignment = Alignment.Center,
                                                            modifier = Modifier
                                                                .fillMaxSize()
                                                                .background(Color.Black)
                                                        ) {
                                                            Icon(
                                                                imageVector = Icons.Default.PlayArrow,
                                                                contentDescription = "Video",
                                                                tint = Color.White,
                                                                modifier = Modifier.size(36.dp)
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                //DEADLINE
                                DeadlinePicker(
                                    deadline = task.value?.deadline,
                                    onDeadlineSelected = { viewModel.setTaskData(deadline = it) },
                                    onDeadlineClear = { viewModel.clearDeadline() }
                                )

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
//                            Display avatar if user has one, otherwise show a placeholder
                                        members.value.forEach { user ->
                                            UserAvatar(
                                                avatar = user.avatar,
                                                size = 36
                                            )
                                        }
                                    }
                                    OutlinedButton(
                                        onClick = { viewModel.showTaskMembersDialog() },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(8.dp),
                                        border = BorderStroke(
                                            1.dp,
                                            MaterialTheme.colorScheme.primary
                                        )
                                    ) {
                                        Text(if (members.value.isNotEmpty()) "Manage Members" else "Add Members +")
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

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        OutlinedTextField(
                                            value = newComment.value,
                                            onValueChange = { viewModel.setNewCommentValue(it) },
                                            placeholder = { Text("Add a comment...") },
                                            modifier = Modifier.weight(1f)
                                        )
                                        Surface(
                                            shape = RoundedCornerShape(8.dp), // Set your desired corner radius
                                            color = MaterialTheme.colorScheme.primary
                                        ) {
                                            IconButton(
                                                onClick = {
                                                    if (newComment.value.isNotBlank()) {
                                                        viewModel.createComment(newComment.value)
                                                        viewModel.setNewCommentValue("")
                                                    }
                                                },
                                                enabled = newComment.value.isNotBlank(),
                                                modifier = Modifier
                                                    .height(56.dp)
                                                    .aspectRatio(1f)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Send,
                                                    contentDescription = "Send",
                                                    tint = MaterialTheme.colorScheme.onPrimary
                                                )
                                            }
                                        }
                                    }


                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(12.dp),
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    ) {
                                        comments.value.forEach { comment ->
                                            Row(
                                                verticalAlignment = Alignment.Top,
                                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Box(modifier = Modifier.padding(top = 4.dp)) {
                                                    UserAvatar(
                                                        avatar = comment.author.avatar,
                                                        size = 36
                                                    )
                                                }

                                                Column {
                                                    Row(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        horizontalArrangement = Arrangement.SpaceBetween
                                                    ) {
                                                        Column {
                                                            Text(
                                                                text = comment.author.username,
                                                                fontWeight = FontWeight.Bold,
                                                                color = MaterialTheme.colorScheme.onBackground
                                                            )
                                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                                Text(
                                                                    text = formatRelativeDate(
                                                                        comment.createdAt?.toLocalDateTime()
                                                                            ?: LocalDateTime.now()
                                                                    ),
                                                                    color = ExtendedTheme.colors.background50,
                                                                    fontSize = 12.sp
                                                                )
                                                                if (comment.edited) {
                                                                    Spacer(
                                                                        modifier = Modifier.width(
                                                                            4.dp
                                                                        )
                                                                    )
                                                                    Icon(
                                                                        imageVector = Icons.Default.Edit,
                                                                        contentDescription = "Edited",
                                                                        tint = ExtendedTheme.colors.background50,
                                                                        modifier = Modifier.size(14.dp)
                                                                    )
                                                                    Spacer(
                                                                        modifier = Modifier.width(
                                                                            2.dp
                                                                        )
                                                                    )
                                                                    Text(
                                                                        text = "(edited)",
                                                                        color = ExtendedTheme.colors.background50,
                                                                        fontSize = 12.sp
                                                                    )
                                                                }
                                                            }
                                                        }
//                                            TODO: Hide this dropdown when current user is not the author of the comment
                                                        DropdownMenu(
                                                            options = listOf(
                                                                DropdownMenuOption(
                                                                    label = "Edit",
                                                                    icon = {
                                                                        Icon(
                                                                            Icons.Default.Edit,
                                                                            contentDescription = "Edit Comment",
                                                                            tint = MaterialTheme.colorScheme.onBackground
                                                                        )
                                                                    },
                                                                    onClick = {
                                                                        viewModel.setSelectedCommentToUpdate(
                                                                            comment
                                                                        )
                                                                        viewModel.showUpdateCommentDialog()
                                                                    }
                                                                ),
                                                                DropdownMenuOption(
                                                                    label = "Delete",
                                                                    icon = {
                                                                        Icon(
                                                                            Icons.Default.Delete,
                                                                            contentDescription = "Delete Comment",
                                                                            tint = MaterialTheme.colorScheme.onBackground
                                                                        )
                                                                    },
//                                                        TODO: Add delete confirmation dialog
                                                                    onClick = {
                                                                        viewModel.deleteComment(
                                                                            comment.id
                                                                        )
                                                                    }
                                                                )
                                                            )
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
                                }
                            }
                        }
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
//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//fun TaskDialogPreviewLight() {
//    TaskSpacesTheme(darkTheme = false) {
//        ExtendedColors(darkTheme = false) {
//            TaskDialog(
//                taskId = 1, onDismissRequest = {},
//                projectViewModel =
//            )
//        }
//    }
//}

/**
 * A preview composable for the [TaskDialog] component using the dark theme.
 *
 * This preview allows developers to visualize how the task dialog appears
 * in dark mode with system UI and background enabled.
 */
//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//fun TaskDialogPreviewDark() {
//    TaskSpacesTheme(darkTheme = true) {
//        ExtendedColors(darkTheme = true) {
//            TaskDialog(
//                taskId = 1, onDismissRequest = {},
//                projectViewModel =
//            )
//        }
//    }
//}
