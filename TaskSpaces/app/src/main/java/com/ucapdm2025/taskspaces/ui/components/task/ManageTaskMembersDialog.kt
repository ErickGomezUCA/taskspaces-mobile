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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.ucapdm2025.taskspaces.data.model.UserModel
import com.ucapdm2025.taskspaces.data.model.relational.WorkspaceMemberModel
import com.ucapdm2025.taskspaces.ui.components.workspace.MemberRoles
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

//TODO: Improve UI, specially the dialog width and select role dropdown
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageTaskMembersDialog(
    onDismissRequest: () -> Unit = {},
    onAddMember: (username: String) -> Unit = { username -> },
    onDeleteMember: (userId: Int) -> Unit = { userId -> },
    members: List<UserModel> = emptyList<UserModel>()
) {
    var addUsername by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Manage members") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                ) {
                    if (members.isEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "No members in this workspace")
                        }
                    } else {

                        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                            members.forEach { member ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
//                                        Avatar and username
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(24.dp)
                                                    .background(Color(0xFFFFA726), CircleShape)
                                            )

                                            Text(
                                                text = member.username,
                                            )
                                        }

//                                        Role dropdown and delete button
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
//                                            TODO: Add confirm dialog
                                            IconButton(onClick = {
                                                onDeleteMember(member.id)
                                            }) {
                                                Icon(
                                                    imageVector = Icons.Default.Delete,
                                                    contentDescription = "Remove member"
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Divider(modifier = Modifier.padding(vertical = 16.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "OR",
                            style = MaterialTheme.typography.bodySmall,
                            color = ExtendedTheme.colors.background75
                        )
                        Text(text = "Invite a new member:")
                    }


//                    TODO: Validate username and avoid sending empty data
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = addUsername,
                            onValueChange = { addUsername = it },
                            label = { Text(text = "Username") }
                        )
                    }
                }

                TextButton(
                    onClick = { onAddMember(addUsername.trim()) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(text = "Invite +")
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
    val members = listOf(
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

    TaskSpacesTheme {
        ExtendedColors {
            ManageTaskMembersDialog(members = members)
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
    val members = listOf(
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


    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            ManageTaskMembersDialog(members = members)
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
