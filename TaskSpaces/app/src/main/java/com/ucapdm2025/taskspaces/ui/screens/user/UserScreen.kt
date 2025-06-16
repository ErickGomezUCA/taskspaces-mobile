package com.ucapdm2025.taskspaces.ui.screens.user

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme


@Composable
fun UserScreen(onNavigateToSettings: () -> Unit = {}) {
    var languageExpanded by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("English") }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //user profile
        Box(
            modifier = Modifier
                .size(72.dp)
                .background(Color(0xFFD6BBFB), shape = CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Firstname Lastname", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Text("@username", color = Color.Gray)

        Spacer(modifier = Modifier.height(32.dp))

        // Personal details button
        CardButton("Personal details", onClick = onNavigateToSettings)

        Spacer(modifier = Modifier.height(16.dp))

        // TODO: Add planScreen
        CardButton("Plan Premium", onClick = { /* Navegar a plan */ })

        Spacer(modifier = Modifier.height(32.dp))

        // Settings
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFF2F2F7))
                .padding(16.dp)
        ) {
            // Language, english for moment
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { languageExpanded = true }
                    .padding(vertical = 8.dp)
            ) {
                Text("Language", modifier = Modifier.weight(1f), color = Color.Gray)
                Box {
                    Text(selectedLanguage, color = Color.Black)
                    DropdownMenu(
                        expanded = languageExpanded,
                        onDismissRequest = { languageExpanded = false }
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
            }

            Divider(color = Color.LightGray, thickness = 1.dp)

            // Notifications switch
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Notifications", modifier = Modifier.weight(1f), color = Color.Gray)
                Switch(
                    checked = notificationsEnabled,
                    onCheckedChange = { notificationsEnabled = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF7E22CE))
                )
            }

            Divider(color = Color.LightGray, thickness = 1.dp)

            // Dark mode switch
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Dark mode", modifier = Modifier.weight(1f), color = Color.Gray)
                Switch(
                    checked = darkModeEnabled,
                    onCheckedChange = { darkModeEnabled = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF7E22CE))
                )
            }
        }
    }
}

@Composable
private fun CardButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF2F2F7))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text, modifier = Modifier.weight(1f), color = Color.Black)
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color(0xFF7E22CE))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserScreenStyled() {
    TaskSpacesTheme {
        UserScreen()
    }
}
