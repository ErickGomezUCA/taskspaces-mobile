package com.ucapdm2025.taskspaces.ui.components.workspace

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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
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
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageMembersDialog(
    onDismissRequest: () -> Unit = {},
    onInviteMember: (username: String, memberRole: MemberRoles) -> Unit = { username, memberRole -> },
    members: List<UserModel> = emptyList<UserModel>()
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedRole by remember { mutableStateOf<MemberRoles>(MemberRoles.READER) }
    var username by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Manage members") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)) {
                    if (members.isEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "No members in this workspace")
                        }
                    } else {
                        members.forEach { member ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .background(Color(0xFFFFA726), CircleShape)
                                )

                                Text(text = member.username, modifier = Modifier.weight(1f))

                                ExposedDropdownMenuBox(
                                    expanded = expanded,
                                    onExpandedChange = { expanded = !expanded },
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                ) {
                                    TextButton(
                                        onClick = { expanded = true }
                                    ) {
                                        Text(selectedRole.toString())
                                    }
                                    ExposedDropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false }
                                    ) {
                                        MemberRoles.entries.forEach { role ->
                                            DropdownMenuItem(
                                                text = { Text(role.toString()) },
                                                onClick = {
                                                    selectedRole = role
                                                    expanded = false
//                                                onRoleChange(member, role)
                                                }
                                            )
                                        }
                                    }
                                }

                                IconButton(onClick = {}) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Remove member"
                                    )
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

                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text(text = "Username") })
                }

                TextButton(
                    onClick = { onInviteMember(username, selectedRole) },
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
fun ManageMembersDialogLightPreview() {
    val members = listOf(
        UserModel(id = 1, fullname = "Test", username = "test", email = "test@email.com")
    )

    TaskSpacesTheme {
        ExtendedColors {
            ManageMembersDialog(members = members)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ManageMembersDialogEmptyLightPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            ManageMembersDialog()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ManageMembersDialogDarkPreview() {
    val members = listOf(
        UserModel(id = 1, fullname = "Test", username = "test", email = "test@email.com")
    )

    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            ManageMembersDialog(members = members)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ManageMembersDialogEmptyDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            ManageMembersDialog()
        }
    }
}
