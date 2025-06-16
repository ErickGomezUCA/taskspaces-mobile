package com.ucapdm2025.taskspaces.ui.screens.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

@Composable
fun SettingsScreen(onNavigateToChangePassword: () -> Unit = {}) {
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
        Text(
            text = "Account Settings",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF3A3A3C),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Upload Image Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF2F2F7)),
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

        // Editable rows
        EditableRow("Name", name) { name = it }
        EditableRow("Username", username) { username = it }
        EditableRow("Email", email) { email = it }
        EditableRow("Description", description) { description = it }
        EditableRow("Nationality", nationality) { nationality = it }

        // Time Zone (simulado)
        RowField("Time Zone", trailing = {
            Text("GMT-6", color = Color.Gray)
        })

        // Password field (navega)
        RowField("Password", trailing = {
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color(0xFF7E22CE))
        }) {
            onNavigateToChangePassword()
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Save Button
        Button(
            onClick = { /* Guardar cambios */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7E22CE)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Save", color = Color.White)
        }
    }
}

@Composable
private fun EditableRow(label: String, value: String, onValueChange: (String) -> Unit) {
    RowField(label, trailing = {
        Text(value, color = Color.Black)
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            Icons.Default.Edit,
            contentDescription = null,
            tint = Color.Gray
        )
    }) {
        // Aquí podrías abrir un diálogo o ir a otra pantalla
    }
}

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
            .background(Color(0xFFF2F2F7))
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

@Preview(showBackground = true)
@Composable
fun PreviewSettingsScreenStyled() {
    TaskSpacesTheme {
        SettingsScreen()
    }
}
