package com.ucapdm2025.taskspaces.ui.screens.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

/**
 * Composable screen that allows the user to change their password.
 *
 * It contains three input fields:
 * - Current password
 * - New password
 * - Re-type new password
 *
 * A button is provided to trigger the password change action.
 *
 * @param onNavigateBack Callback to be triggered when the user presses the change button.
 */
@Composable
fun ChangePasswordScreen(onNavigateBack: () -> Unit = {}) {
    // TODO: Replace these local states with values provided by the ViewModel once backend integration is available
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = "Change password",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 52.dp)
        )

        // Instructional message
        Text(
            text = "Your password must be at least 6 characters and should include a combination of numbers, letters and special characters (!\$@%)",
            fontSize = 14.sp,
            color = Color(0xFF8E8E93),
            textAlign = TextAlign.Start,
            lineHeight = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp)
        )

        // Input for current password
        // TODO: Validate and update current password through ViewModel
        OutlinedTextField(
            value = currentPassword,
            onValueChange = { currentPassword = it },
            label = { Text("Current password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(12.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        // Input for new password
        // TODO: Validate and update new password through ViewModel
        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("New password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(12.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        // Input for confirming new password
        // TODO: Validate password confirmation through ViewModel
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Re-type new password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(12.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Submit button
        // TODO: Hook this button to ViewModel function that calls API to change the password
        Button(
            onClick = {
                // This will be replaced by: viewModel.changePassword(...)
                onNavigateBack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9966E2)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = "Change password", color = Color.White)
        }
    }
}

/**
 * Preview of the ChangePasswordScreen in light mode.
 */
@Preview(showBackground = true)
@Composable
fun ChangePassPreviewLight() {
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            ChangePasswordScreen()
        }
    }
}

/**
 * Preview of the ChangePasswordScreen in dark mode.
 */
@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun ChangePassPreviewDark() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            ChangePasswordScreen()
        }
    }
}
