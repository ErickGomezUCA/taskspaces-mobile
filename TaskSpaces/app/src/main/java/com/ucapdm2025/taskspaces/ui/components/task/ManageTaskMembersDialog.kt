package com.ucapdm2025.taskspaces.ui.components.task


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
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
import com.ucapdm2025.taskspaces.data.model.UserModel
import com.ucapdm2025.taskspaces.ui.components.general.UserAvatar
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageTaskMembersDialog(
    onDismissRequest: () -> Unit = {},
    workspaceMembers: List<UserModel> = emptyList(),
    assignedMembers: List<UserModel> = emptyList(),
    onAssignMember: (userId: Int) -> Unit = {userId ->},
    onUnassignMember: (userId: Int) -> Unit = {userId ->},
) {

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Manage members") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                if (workspaceMembers.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "No members in this workspace")
                    }
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                        workspaceMembers.forEach { member ->
                            var checked by remember { mutableStateOf<Boolean>(assignedMembers.any { member.id == it.id }) }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                androidx.compose.material3.Checkbox(
                                    checked = checked,
                                    onCheckedChange = { isChecked ->
                                        checked = isChecked

                                        if (isChecked) {
                                            onAssignMember(member.id)
                                        } else {
                                            onUnassignMember(member.id)
                                        }
                                    }
                                )
                                UserAvatar(avatar = member.avatar, size = 24)
                                Text(
                                    text = member.username,
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                        .weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onDismissRequest() },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
            ) { Text(text = "Close") }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun ManageTaskMembersDialogLightPreview() {
    val workspaceMembers = listOf(
        UserModel(
            id = 1,
            fullname = "Test 1",
            username = "test 1",
            email = "test@email.com"
        ),
        UserModel(
            id = 2,
            fullname = "Test 2",
            username = "test 2",
            email = "test@email.com"
        ),
        UserModel(
            id = 3,
            fullname = "Test 3",
            username = "test 3",
            email = "test@email.com"
        ),
    )

    val assignedMembers = listOf(
        UserModel(
            id = 1,
            fullname = "Test 1",
            username = "test 1",
            email = "test@email.com"
        ),
    )

    TaskSpacesTheme {
        ExtendedColors {
            ManageTaskMembersDialog(workspaceMembers = workspaceMembers, assignedMembers = assignedMembers)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ManageTaskMembersDialogEmptyLightPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            ManageTaskMembersDialog()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ManageTaskMembersDialogDarkPreview() {
    val workspaceMembers = listOf(
        UserModel(
            id = 1,
            fullname = "Test 1",
            username = "test 1",
            email = "test@email.com"
        ),
        UserModel(
            id = 2,
            fullname = "Test 2",
            username = "test 2",
            email = "test@email.com"
        ),
        UserModel(
            id = 3,
            fullname = "Test 3",
            username = "test 3",
            email = "test@email.com"
        ),
    )

    val assignedMembers = listOf(
        UserModel(
            id = 1,
            fullname = "Test 1",
            username = "test 1",
            email = "test@email.com"
        ),
    )

    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            ManageTaskMembersDialog(workspaceMembers = workspaceMembers, assignedMembers = assignedMembers)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ManageTaskMembersDialogEmptyDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            ManageTaskMembersDialog()
        }
    }
}
