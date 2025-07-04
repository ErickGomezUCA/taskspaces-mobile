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
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.ucapdm2025.taskspaces.helpers.UiState
import com.ucapdm2025.taskspaces.ui.screens.login.LoginViewModel


/**
 * Composable that displays the user profile screen including:
 * - Personal info
 * - Plan section
 * - Settings (language, notifications, dark mode)
 *
 * @param onNavigateToSettings Triggered when "Personal details" is tapped.
 */
@Composable
fun UserScreen(
    onNavigateToSettings: () -> Unit = {},
    viewModel: UserDetailsViewModel = viewModel(factory = UserDetailsViewModel.Factory),
    loginViewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory)
) {
    val scrollState = rememberScrollState()
    val userState by viewModel.user.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (userState) {
            is UiState.Loading -> {
                CircularProgressIndicator()
            }

            is UiState.Error -> {
                Text(
                    text = (userState as UiState.Error).message,
                    color = MaterialTheme.colorScheme.error
                )
            }

            is UiState.Success -> {
                val user = (userState as UiState.Success).data

                if (user.avatar != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(user.avatar)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFD6BBFB))
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = user.fullname,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(text = "@${user.username}", color = Color.Gray)
            }
        }

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

        Button(
            onClick = { loginViewModel.logout() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Log Out")
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


/**
 * Preview for the User screen using the light theme.
 *
 * Displays the UserScreen composable with light mode styles applied
 * via TaskSpacesTheme and ExtendedColors.
 *
 */
@Preview(showBackground = true)
@Composable
fun UserScreenPreviewLight() {
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            UserScreen()
        }
    }
}

/**
 * Preview for the User screen using the dark theme.
 *
 * Displays the UserScreen composable with dark mode styles applied,
 * including a custom dark background color for better contrast.
 *
 */
@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun UserScreenPreviewDark() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            UserScreen()
        }
    }
}
