package com.ucapdm2025.taskspaces.ui.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

/**
 * Displays the Account Settings screen with editable user fields and actions.
 *
 * @param onNavigateToChangePassword Callback for navigating to the change password screen.
 */
@Composable
fun SettingsScreen(onNavigateToChangePassword: () -> Unit = {}) {
    // TODO: Replace these local states with values provided by the ViewModel once backend integration is available
    var name by remember { mutableStateOf("John Doe") }
    var username by remember { mutableStateOf("johndoe") }
    var email by remember { mutableStateOf("johndoe@gmail.com") }
    var description by remember { mutableStateOf("Add description") }
    var nationality by remember { mutableStateOf("El Salvador") }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = "Account Settings",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // TODO: Add Upload Image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(ExtendedTheme.colors.background05),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFD6BBFB))
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Upload image", color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Editable Fields
        EditableRow("Name", name) { name = it }
        EditableRow("Username", username) { username = it }
        EditableRow("Email", email) { email = it }
        EditableRow("Description", description) { description = it }
        EditableRow("Nationality", nationality) { nationality = it }
        RowField("Time Zone", trailing = {
            Text("GMT-6", color = Color.Gray)
        })

        // Password field
        RowField("Password", trailing = {
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color(0xFF9966E2))
        }) {
            onNavigateToChangePassword()
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Save Button
        // TODO: Implement saving changes to the backend via ViewModel
        Button(
            onClick = {  /* TODO:Save changes */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Save", color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

/**
 * Reusable row that displays a label and editable value.
 *
 * @param label Field name.
 * @param value Current value shown to user.
 * @param onValueChange Callback to update the value.
 */
@Composable
private fun EditableRow(label: String, value: String, onValueChange: (String) -> Unit) {
    RowField(label, trailing = {
        Text(value, color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            Icons.Default.Edit,
            contentDescription = null,
            tint = Color.Gray
        )
    }) {
        // TODO: Could open a dialog or navigate elsewhere
    }
}

/**
 * Generic field row for settings with optional click action and trailing content.
 *
 * @param label Field label.
 * @param trailing Composable placed at end of row.
 * @param onClick Optional row click action.
 */
@Composable
private fun RowField(
    label: String,
    trailing: @Composable RowScope.() -> Unit = {},
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 0.3.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(12.dp)
            )
            .background(MaterialTheme.colorScheme.background) // fondo blanco o dark según tema
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(label, color = Color.Gray, modifier = Modifier.weight(1f))
            trailing()
        }
    }
}


/**
 * Preview for SettingsScreen in light theme.
 */
@Preview(showBackground = true)
@Composable
fun SettingsScreenPreviewLight() {
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            SettingsScreen()
        }
    }
}

/**
 * Preview for SettingsScreen in dark theme.
 */
@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun SettingsScreenPreviewDark() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            SettingsScreen()
        }
    }
}

