package com.ucapdm2025.taskspaces.ui.components.workspace

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ucapdm2025.taskspaces.data.model.UserModel
import com.ucapdm2025.taskspaces.data.model.relational.WorkspaceMemberModel
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

//TODO: Improve UI, specially the dialog width and select role dropdown
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageMembersDialog(
    onDismissRequest: () -> Unit = {},
    onInviteMember: (username: String, memberRole: MemberRoles) -> Unit = { _, _ -> },
    onRoleUpdated: (userId: Int, memberRole: MemberRoles) -> Unit = { _, _ -> },
    onDeleteMember: (userId: Int) -> Unit = {},
    members: List<WorkspaceMemberModel> = emptyList()
) {
    var inviteUsername by remember { mutableStateOf("") }
    var inviteRole by remember { mutableStateOf(MemberRoles.READER) }
    var inviteRoleExpanded by remember { mutableStateOf(false) }
    var wasInviteAttempted by remember { mutableStateOf(false) }
    val isUsernameValid = inviteUsername.trim().isNotEmpty()

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
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        var expanded by remember { mutableStateOf(false) }
                                        var selectedRole by remember {
                                            mutableStateOf(member.memberRole)
                                        }

                                        // Avatar and username
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

                                            Text(text = member.user.username)
                                        }

                                        // Role dropdown and delete button
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            ExposedDropdownMenuBox(
                                                expanded = expanded,
                                                onExpandedChange = { expanded = !expanded },
                                                modifier = Modifier.weight(1f)
                                            ) {
                                                OutlinedTextField(
                                                    value = selectedRole.value,
                                                    onValueChange = {},
                                                    readOnly = true,
                                                    label = { Text("Role") },
                                                    trailingIcon = {
                                                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                                    },
                                                    modifier = Modifier
                                                        .menuAnchor()
                                                        .clickable { expanded = true }
                                                )
                                                ExposedDropdownMenu(
                                                    expanded = expanded,
                                                    onDismissRequest = { expanded = false }
                                                ) {
                                                    MemberRoles.entries.forEach { role ->
                                                        DropdownMenuItem(
                                                            text = { Text(role.value) },
                                                            onClick = {
                                                                selectedRole = role
                                                                expanded = false
                                                                onRoleUpdated(member.user.id, role)
                                                            }
                                                        )
                                                    }
                                                }
                                            }

                                            // TODO: Add confirm dialog
                                            IconButton(onClick = {
                                                onDeleteMember(member.user.id)
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

                    // TODO: Validate username and avoid sending empty data
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = inviteUsername,
                            onValueChange = {
                                if (!it.contains(" ")) {
                                    inviteUsername = it
                                    if (wasInviteAttempted) wasInviteAttempted = false
                                }
                            }
                            ,
                            label = { Text(text = "Username") },
                            isError = wasInviteAttempted && !isUsernameValid,
                            supportingText = {
                                if (wasInviteAttempted && !isUsernameValid) {
                                    Text("Username is required", color = MaterialTheme.colorScheme.error)
                                }
                            }
                        )

                        ExposedDropdownMenuBox(
                            expanded = inviteRoleExpanded,
                            onExpandedChange = { inviteRoleExpanded = !inviteRoleExpanded },
                        ) {
                            OutlinedTextField(
                                value = inviteRole.value,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Role") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = inviteRoleExpanded)
                                },
                                modifier = Modifier
                                    .menuAnchor()
                                    .clickable { inviteRoleExpanded = true }
                            )
                            ExposedDropdownMenu(
                                expanded = inviteRoleExpanded,
                                onDismissRequest = { inviteRoleExpanded = false }
                            ) {
                                MemberRoles.entries.forEach { role ->
                                    DropdownMenuItem(
                                        text = { Text(role.value) },
                                        onClick = {
                                            inviteRole = role
                                            inviteRoleExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    TextButton(
                        onClick = {
                            wasInviteAttempted = true
                            if (isUsernameValid) {
                                onInviteMember(inviteUsername.trim(), inviteRole)
                                inviteUsername = ""
                                wasInviteAttempted = false
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Text(text = "Invite +")
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismissRequest,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(text = "Close")
            }
        }
    )
}
