package com.ucapdm2025.taskspaces.ui.screens.signup

import android.Manifest
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.rememberAsyncImagePainter
import com.ucapdm2025.taskspaces.R
import com.ucapdm2025.taskspaces.TaskSpacesApplication
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import com.ucapdm2025.taskspaces.ui.theme.PrimaryLight100
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

/**
 * A composable function that displays the sign-up screen UI.
 *
 * This screen includes input fields for name, username, email, password, and confirm password.
 * It displays a background image and branding similar to the login screen.
 *
 * @param onSignUp Callback triggered when the user submits valid information.
 * @param onNavigateToLogin Callback triggered when the user taps the "Log in" link.
 */
@Composable
fun SignUpScreen(
    onSignUp: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
) {
    val application = LocalContext.current.applicationContext as TaskSpacesApplication
    val userRepository = application.appProvider.provideUserRepository()
    val authRepository = application.appProvider.provideAuthRepository()
    val viewModel: SignUpViewModel =
        viewModel(factory = SignUpViewModelFactory(userRepository, authRepository))

    val fullname = viewModel.fullname.collectAsStateWithLifecycle()
    val username = viewModel.username.collectAsStateWithLifecycle()
    val email = viewModel.email.collectAsStateWithLifecycle()
    val password = viewModel.password.collectAsStateWithLifecycle()
    val confirmPassword = viewModel.confirmPassword.collectAsStateWithLifecycle()
    val authToken = viewModel.authToken.collectAsStateWithLifecycle()
    val avatarUri = viewModel.avatarUri.collectAsStateWithLifecycle()
    val avatarUrl = viewModel.avatarUrl.collectAsStateWithLifecycle()
    val isUploadingAvatar = viewModel.isUploadingAvatar.collectAsStateWithLifecycle()
    val avatarUploadError = viewModel.avatarUploadError.collectAsStateWithLifecycle()

    val fullnameError = viewModel.fullnameError.collectAsStateWithLifecycle()
    val usernameError = viewModel.usernameError.collectAsStateWithLifecycle()
    val emailError = viewModel.emailError.collectAsStateWithLifecycle()
    val passwordError = viewModel.passwordError.collectAsStateWithLifecycle()
    val confirmPasswordError = viewModel.confirmPasswordError.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val pickImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        viewModel.setAvatarUri(uri)
        if (uri != null) {
            viewModel.uploadAvatar(uri)
        }
    }

    LaunchedEffect(authToken.value) {
        if (authToken.value.isNotEmpty()) {
            onSignUp()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Welcome to",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "TaskSpaces",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    ),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }


            Spacer(modifier = Modifier.height(32.dp))

            TextField(
                value = fullname.value,
                onValueChange = { viewModel.setFullname(it) },
                label = { Text("Name") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                ),
                isError = fullnameError.value != null
            )
            if (fullnameError.value != null) {
                Text(
                    text = fullnameError.value ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = username.value,
                onValueChange = { viewModel.setUsername(it) },
                label = { Text("Username") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                ),
                isError = usernameError.value != null
            )
            if (usernameError.value != null) {
                Text(
                    text = usernameError.value ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = email.value,
                onValueChange = { viewModel.setEmail(it) },
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                ),
                isError = emailError.value != null
            )
            if (emailError.value != null) {
                Text(
                    text = emailError.value ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Avatar Picker Section styled like inputs
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Upload profile image",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.background, RoundedCornerShape(16.dp))
                        .clickable(enabled = !isUploadingAvatar.value) { pickImageLauncher.launch("image/*") }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (avatarUri.value == null) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                        }
                        if (isUploadingAvatar.value) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(32.dp),
                                color = MaterialTheme.colorScheme.primary,
                                strokeWidth = 3.dp
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Uploading...",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        } else if (avatarUri.value != null) {
                            Image(
                                painter = rememberAsyncImagePainter(avatarUri.value),
                                contentDescription = "Avatar",
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(RoundedCornerShape(50)) // Circular shape
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Change image",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        } else {
                            Text(
                                text = "Select an image",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
                if (avatarUploadError.value != null) {
                    Text(
                        text = avatarUploadError.value ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = password.value,
                onValueChange = { viewModel.setPassword(it) },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                ),
                isError = passwordError.value != null
            )
            if (passwordError.value != null) {
                Text(
                    text = passwordError.value ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = confirmPassword.value,
                onValueChange = { viewModel.setConfirmPassword(it) },
                label = { Text("Confirm password") },
                visualTransformation = PasswordVisualTransformation(),
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                ),
                isError = confirmPasswordError.value != null
            )
            if (confirmPasswordError.value != null) {
                Text(
                    text = confirmPasswordError.value ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Have an account?",
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(0.dp)
                )
                TextButton(onClick = onNavigateToLogin, modifier = Modifier.padding(0.dp)) {
                    Text("Log in", color = PrimaryLight100)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.signup()
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Sign up")
            }
        }
    }
}

/**
 * A preview composable for the [SignUpScreen] using the light theme.
 */
@Preview()
@Composable
fun PreviewSignUpScreenLight() {
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            SignUpScreen()
        }
    }
}

/**
 * A preview composable for the [SignUpScreen] using the dark theme.
 */
@Preview()
@Composable
fun PreviewSignUpScreenDark() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            SignUpScreen()
        }
    }
}
