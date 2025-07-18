package com.ucapdm2025.taskspaces.ui.screens.workspace

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucapdm2025.taskspaces.TaskSpacesApplication
import com.ucapdm2025.taskspaces.helpers.UiState
import com.ucapdm2025.taskspaces.ui.components.general.Container
import com.ucapdm2025.taskspaces.ui.components.general.DropdownMenuOption
import com.ucapdm2025.taskspaces.ui.components.general.FeedbackIcon
import com.ucapdm2025.taskspaces.ui.components.general.FloatingStatusDialog
import com.ucapdm2025.taskspaces.ui.components.general.NotificationHost
import com.ucapdm2025.taskspaces.ui.components.workspace.ManageMembersDialog
import com.ucapdm2025.taskspaces.ui.components.workspace.MemberRoles
import com.ucapdm2025.taskspaces.ui.components.workspace.ProjectCard
import com.ucapdm2025.taskspaces.ui.components.workspace.UserCard
import com.ucapdm2025.taskspaces.ui.components.workspace.WorkspaceEditMode
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import com.ucapdm2025.taskspaces.ui.theme.OutfitTypography
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

/**
 * Displays the workspace details screen, including sections for projects and members.
 *
 * Fetches workspace, project, and member data using the provided [workspaceId] and displays them
 * in a structured layout. If the workspace is not found, a feedback message is shown.
 *
 * @param workspaceId The unique identifier of the workspace to display.
 * @param onProjectCardClick Callback function to handle clicks on project cards, passing the project ID.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkspaceScreen(
    workspaceId: Int,
    onNavigateProject: (Int) -> Unit,
) {
//    Retrieve dependencies and ViewModel here because i cannot pass them as parameters with companion objects (like the traditional way)
    val application = LocalContext.current.applicationContext as TaskSpacesApplication
    val workspaceRepository = application.appProvider.provideWorkspaceRepository()
    val memberRoleRepository = application.appProvider.provideMemberRoleRepository()
    val projectRepository = application.appProvider.provideProjectRepository()

    val viewModel: WorkspaceViewModel = viewModel(
        factory = WorkspaceViewModelFactory(
            workspaceId,
            workspaceRepository,
            memberRoleRepository,
            projectRepository
        )
    )

    val workspaceState = viewModel.workspaceState.collectAsStateWithLifecycle()
    val projectsState = viewModel.projectsState.collectAsStateWithLifecycle()
    val showProjectDialog = viewModel.showProjectDialog.collectAsStateWithLifecycle()
    val projectDialogData = viewModel.projectDialogData.collectAsStateWithLifecycle()
    val editMode = viewModel.editMode.collectAsStateWithLifecycle()
    val selectedProjectId = viewModel.selectedProjectId.collectAsStateWithLifecycle()
    val membersState = viewModel.membersState.collectAsStateWithLifecycle()
    val showManageMembersDialog = viewModel.showManageMembersDialog.collectAsStateWithLifecycle()
    val notificationState = remember { mutableStateOf<UiEvent?>(null) }
    val wasProjectCreateAttempted = viewModel.wasProjectCreateAttempted.collectAsStateWithLifecycle()
    val showProjectDeleteConfirmationDialog = viewModel.showProjectDeleteConfirmationDialog.collectAsStateWithLifecycle()
    val pendingProjectToDeleteId = viewModel.pendingProjectToDeleteId.collectAsStateWithLifecycle()

//    Para manejar los roles de un workspace, ahora puedes hacerlo con:
//
//    viewModel.hasSufficientPermissions(MemberRoles.ADMIN)
//
//    donde: MemberRoles determina el rol necesario para mostrar o realizar una accion
//    puedes hacer algo como
//
//    if (viewModel.hasSufficientPermissions(MemberRoles.COLLABORATOR)) {
//      ... mostrar algo solo para colaboradores y admin
//    }
//
//    para poder mostrar u ocultar elementos de la UI según el rol del usuario en el workspace

    // Escuchar los eventos del ViewModel
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { evt ->
            notificationState.value = evt
            delay(3000)
            notificationState.value = null
        }
    }

    when (val state = workspaceState.value) {
        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

//    TODO: Show error and loading states
//    Show feedback icon if the workspace is not found
            return
        }

        is UiState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                FeedbackIcon(
                    icon = Icons.Default.Close,
                    title = state.message,
                )
            }
            return
        }

        is UiState.Success -> {
            val workspace = state.data
            if (workspace == null) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    FeedbackIcon(
                        icon = Icons.Default.Close,
                        title = "Workspace not found",
                    )
                }
                return
            }


//    Create project dialog
            if (showProjectDialog.value) {
                AlertDialog(
                    onDismissRequest = { viewModel.hideDialog() },
                    title = { Text(text = if (editMode.value == WorkspaceEditMode.UPDATE) "Update workspace" else "Create a new workspace") },
                    text = {
//                    TODO: Add Icon field
                        Column {
                            TextField(
                                value = projectDialogData.value,
                                onValueChange = { viewModel.setProjectDialogData(it) },
                                label = { Text(text = "Project Title") },
                                placeholder = { Text(text = "Enter project title") },
                                isError = wasProjectCreateAttempted.value && projectDialogData.value.isBlank(),
                                supportingText = {
                                    if (wasProjectCreateAttempted.value && projectDialogData.value.isBlank()) {
                                        Text(
                                            "Project title cannot be empty",
                                            color = MaterialTheme.colorScheme.error
                                        )
                                    }
                                }
                            )

                        }
                    },
                    confirmButton = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            OutlinedButton(
                                onClick = { viewModel.hideDialog() },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                            ) { Text(text = "Cancel") }

                            Button(
                                onClick = {
                                    viewModel.markProjectCreateAttempted()

                                    if (projectDialogData.value.isBlank()) {
                                        return@Button
                                    }

                                    if (editMode.value == WorkspaceEditMode.UPDATE) {
                                        viewModel.updateProject(
                                            id = selectedProjectId.value ?: 0,
                                            title = projectDialogData.value,
                                            icon = "",
                                        )
                                        viewModel.setSelectedProjectId(null)
                                    } else {
                                        viewModel.createProject(
                                            title = projectDialogData.value,
                                            icon = ""
                                        )
                                    }

                                    viewModel.setEditMode(WorkspaceEditMode.NONE)
                                    viewModel.hideDialog()
                                    viewModel.resetProjectCreateAttempted()
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp),
                            ) { Text(text = if (editMode.value == WorkspaceEditMode.UPDATE) "Update" else "Create") }
                        }
                    }
                )
            }

            // Project Delete confirmation dialog
            if (showProjectDeleteConfirmationDialog.value) {
                AlertDialog(
                    onDismissRequest = { viewModel.hideProjectDeleteConfirmationDialog() },
                    title = { Text(text = "Confirm Deletion") },
                    text = { Text(text = "Are you sure you want to delete this project?") },
                    confirmButton = {
                        Button(
                            onClick = {
                                pendingProjectToDeleteId.value?.let { projectId ->
                                    if (viewModel.hasSufficientPermissions(MemberRoles.ADMIN)) {
                                        viewModel.deleteProject(projectId)
                                        viewModel.setEditMode(WorkspaceEditMode.NONE) // Exit delete mode after successful deletion
                                    }
                                }
                                viewModel.hideProjectDeleteConfirmationDialog()
                            }
                        ) {
                            Text("Delete")
                        }
                    },
                    dismissButton = {
                        OutlinedButton(
                            onClick = { viewModel.hideProjectDeleteConfirmationDialog() }
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }


            if (showManageMembersDialog.value) {
                ManageMembersDialog(
                    onDismissRequest = { viewModel.hideManageMembersDialog() },
                    onInviteMember = { username, memberRole ->
                        viewModel.hideManageMembersDialog()
                        viewModel.inviteMember(username, memberRole)
                    },
                    onRoleUpdated = { userId, memberRole ->
                        viewModel.updateMemberRole(userId = userId, newMemberRole = memberRole)
                    },
                    onDeleteMember = { userId ->
                        viewModel.removeMember(userId)
                    },
                    members = (membersState.value as? UiState.Success)?.data ?: emptyList()
                )
            }

            Box(modifier = Modifier.fillMaxSize()) {
                NotificationHost(
                    event = notificationState.value,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 250.dp)
                        .zIndex(1f)
                )
                if (editMode.value != WorkspaceEditMode.NONE) {
                    FloatingStatusDialog(
                        onClose = { viewModel.setEditMode(WorkspaceEditMode.NONE) },
                        message = when (editMode.value) {
                            WorkspaceEditMode.DELETE -> "On Delete Mode"
                            WorkspaceEditMode.UPDATE -> "On Update Mode"
                            else -> "Invalid Mode"
                        }
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = ExtendedTheme.colors.background05,
                            shape = RoundedCornerShape(size = 24.dp)
                        )
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // Workspace title
                    item {
                        Text(
                            text = workspace.title,
                            style = OutfitTypography.headlineSmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }


                    // Projects Section
                    item {
                        Container(
                            title = "Projects",
                            dropdownMenuOptions = if (viewModel.hasSufficientPermissions(MemberRoles.ADMIN)) {
                                listOf(
                                    DropdownMenuOption(
                                        label = "Delete",
                                        icon = {
                                            Icon(
                                                Icons.Outlined.Delete,
                                                contentDescription = "Delete icon"
                                            )
                                        },
                                        onClick = { viewModel.setEditMode(WorkspaceEditMode.DELETE) }
                                    ),
                                    DropdownMenuOption(
                                        label = "Update",
                                        icon = {
                                            Icon(
                                                Icons.Outlined.Sync,
                                                contentDescription = "Edit icon"
                                            )
                                        },
                                        onClick = { viewModel.setEditMode(WorkspaceEditMode.UPDATE) }
                                    )
                                )
                            } else emptyList()
                        )
                        {
                            val projectList =
                                (projectsState.value as? UiState.Success)?.data ?: emptyList()
                            if (projectList.isNotEmpty()) {
                                //                    Show projects section
                                val chunkedProjects = projectList.chunked(2)

                                Column(
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    chunkedProjects.forEach { rowItems ->
                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(
                                                8.dp,
                                                Alignment.CenterHorizontally
                                            ),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        ) {
                                            rowItems.forEach { project ->
                                                ProjectCard(
                                                    name = project.title,
                                                    modifier = Modifier.weight(1f),
                                                    onClick = {
                                                        when (editMode.value) {
//                            Set the selected project ID when in update mode and show the dialog

                                                            WorkspaceEditMode.UPDATE -> {
                                                                if (viewModel.hasSufficientPermissions(MemberRoles.ADMIN)) {
                                                                    viewModel.setSelectedProjectId(
                                                                        project.id
                                                                    )
                                                                    viewModel.setProjectDialogData(
                                                                        project.title
                                                                    )
                                                                    viewModel.showDialog()
                                                                }
                                                            }

//                                Delete the project clicked when in delete mode
//                                TODO: Add a confirmation dialog before deleting
                                                            WorkspaceEditMode.DELETE -> {
                                                                viewModel.showProjectDeleteConfirmationDialog(project.id)

                                                            }

                                                            else -> onNavigateProject(project.id)
                                                        }
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                            } else {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "No projects found...",
                                        color = ExtendedTheme.colors.onBackground50
                                    )
                                }

                            }

                            // "Create new project" button
                            if (viewModel.hasSufficientPermissions(MemberRoles.ADMIN)) {
                                TextButton(
                                    onClick = { viewModel.showDialog() },
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(40.dp),
                                ) {
                                    Text(
                                        text = "Create new project",
                                        style = OutfitTypography.bodyMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Add icon",
                                        modifier = Modifier
                                            .padding(start = 4.dp)
                                            .size(16.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // "See more" button
                            if (projectList.size > 2) {
                                Button(
                                    onClick = { /* // TODO: I shouldn't handle this logic directly here in the Composable.
                    //  This should be delegated to the ViewModel to follow proper architecture practices. */
                                    },
                                    modifier = Modifier
                                        .background(
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = RoundedCornerShape(size = 8.dp)
                                        )
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = "See more",
                                        style = OutfitTypography.bodyMedium,
                                        color = MaterialTheme.colorScheme.background
                                    )
                                }
                            }
                        }
                    }


                    // Members Section
                    val memberList = (membersState.value as? UiState.Success)?.data ?: emptyList()

                    item {
                        Container(title = "Members") {
                            if (memberList.isEmpty()) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "No members in this workspace")
                                }
                            } else {
                                val chunkedUsers = memberList.chunked(3)

                                Column(
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    chunkedUsers.forEach { rowItems ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(120.dp),
                                            horizontalArrangement = Arrangement.SpaceEvenly,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            rowItems.forEach { member ->
                                                UserCard(
                                                    username = member.user.username,
                                                    avatar = member.user.avatar.toString(),
                                                    modifier = Modifier
                                                        .width(80.dp)
                                                        .fillMaxHeight()
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            if (viewModel.hasSufficientPermissions(MemberRoles.ADMIN)) {
                                Button(
                                    onClick = { viewModel.showManageMembersDialog() },
                                    modifier = Modifier
                                        .background(
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = RoundedCornerShape(size = 8.dp)
                                        )
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Manage members",
                                        style = OutfitTypography.bodyMedium,
                                        color = MaterialTheme.colorScheme.background
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

/**
 * Preview of [WorkspaceScreen] in light theme with a valid workspace ID.
 *
 * Shows the workspace screen as it would appear in light mode.
 */
@Preview(showBackground = true)
@Composable
fun WorkspaceScreenPreviewLight() {
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            WorkspaceScreen(workspaceId = 1, onNavigateProject = {})
        }
    }
}

/**
 * Preview of [WorkspaceScreen] in light theme when the workspace is not found.
 *
 * Demonstrates the not-found state in light mode.
 */
@Preview(showBackground = true)
@Composable
fun WorkspaceScreenNotFoundPreviewLight() {
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            WorkspaceScreen(workspaceId = 0, onNavigateProject = {})
        }
    }
}

/**
 * Preview of [WorkspaceScreen] in dark theme with a valid workspace ID.
 *
 * Shows the workspace screen as it would appear in dark mode.
 */
@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun WorkspaceScreenPreviewDark() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            WorkspaceScreen(workspaceId = 1, onNavigateProject = {})
        }
    }
}

/**
 * Preview of [WorkspaceScreen] in dark theme when the workspace is not found.
 *
 * Demonstrates the not-found state in dark mode.
 */
@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun WorkspaceScreenNotFoundPreviewDark() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            WorkspaceScreen(workspaceId = 0, onNavigateProject = {})
        }
    }
}


