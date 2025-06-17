package com.ucapdm2025.taskspaces.ui.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll


/**
 * Composable that displays the user profile screen including:
 * - Personal info
 * - Plan section
 * - Settings (language, notifications, dark mode)
 *
 * @param onNavigateToSettings Triggered when "Personal details" is tapped.
 */
@Composable
fun UserScreen(onNavigateToSettings: () -> Unit = {}) {
    // TODO: Replace these UI states with values from the ViewModel once the backend is connected
    var languageExpanded by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("English") }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(true) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile picture placeholder
        Box(
            modifier = Modifier
                .size(72.dp)
                .background(Color(0xFFD6BBFB), shape = CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // User display name and handle
        // TODO: Replace with dynamic user data (name and handle) from ViewModel
        Text("Firstname Lastname", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
        Text("@username", color = Color.Gray)

        Spacer(modifier = Modifier.height(32.dp))

        // Section: Account settings
        Text(
            text = "Account settings",
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        CardButton("Personal details", onClick = onNavigateToSettings)

        Spacer(modifier = Modifier.height(16.dp))

        // Section: Plan
        Text(
            text = "Organizations / Plan",
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        CardButton("Plan Premium", onClick = { })

        Spacer(modifier = Modifier.height(32.dp))

        // Section: Settings
        Text(
            text = "Settings",
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .border(0.3.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(16.dp))
                .background(ExtendedTheme.colors.background05)

        ) {
            // Language option
            // TODO: Language selection will eventually be stored/loaded from user preferences via ViewModel
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                    contentAlignment = Alignment.CenterEnd
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { languageExpanded = true }
                ) {
                    Text("Language", color = Color.Gray)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(selectedLanguage, color = Color.Gray)
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            modifier = Modifier.rotate(90f),
                            contentDescription = "Expand language menu",
                            tint = Color(0xFF9966E2)
                        )
                    }
                }

            DropdownMenu(
                expanded = languageExpanded,
                onDismissRequest = { languageExpanded = false },
                modifier = Modifier .width(IntrinsicSize.Min) .align(Alignment.TopEnd)
            ) {
                DropdownMenuItem(
                    text = { Text("English") },
                    onClick = {
                        selectedLanguage = "English"
                        languageExpanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Español") },
                    onClick = {
                        selectedLanguage = "Español"
                        languageExpanded = false

                         }
                    )
                }
            }

            Divider(color = Color.LightGray, thickness = 1.dp)

            // Notifications toggle
            // TODO: Notification setting should be read and written via ViewModel
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            ) {
                Text("Notifications", color = Color.Gray)
                Switch(
                    checked = notificationsEnabled,
                    onCheckedChange = { notificationsEnabled = it },
                    modifier = Modifier.align(Alignment.CenterVertically),
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color(0xFF9966E2),
                        checkedTrackColor = Color(0xFF9966E2),
                        checkedBorderColor = Color(0xFF9966E2),
                        uncheckedThumbColor = Color(0xFF9966E2)
                    )
                )
            }

            Divider(color = Color.LightGray, thickness = 1.dp)

            // Dark mode toggle
            // TODO: Dark mode setting should sync with app theme or user preference via ViewModel
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            ) {
                Text("Dark mode", color = Color.Gray)
                Switch(
                    checked = darkModeEnabled,
                    onCheckedChange = { darkModeEnabled = it },
                    modifier = Modifier.align(Alignment.CenterVertically),
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color(0xFF9966E2),
                        checkedTrackColor = Color(0xFF9966E2),
                        checkedBorderColor = Color(0xFF9966E2),
                        uncheckedThumbColor = Color(0xFF9966E2)
                    )
                )
            }
        }
    }
}

/**
 * Reusable button with label and trailing arrow icon.
 *
 * @param text Button text label.
 * @param onClick Click callback.
 */
@Composable
private fun CardButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(0.3.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
            .background(ExtendedTheme.colors.background05)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text, modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.onBackground)
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color(0xFF9966E2))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserScreenPreviewLight() {
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            UserScreen()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun UserScreenPreviewDark() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            UserScreen()
        }
    }
}
