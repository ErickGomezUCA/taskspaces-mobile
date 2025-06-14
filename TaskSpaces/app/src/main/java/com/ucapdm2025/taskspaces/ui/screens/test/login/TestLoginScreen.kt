package com.ucapdm2025.taskspaces.ui.screens.test.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucapdm2025.taskspaces.ui.components.general.Container
import com.ucapdm2025.taskspaces.ui.screens.login.LoginViewModel

@Composable
fun TestLoginScreen(
    viewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory),
    onSuccessfulLogin: () -> Unit
) {
    val authToken = viewModel.authToken.collectAsStateWithLifecycle()

    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (authToken.value == "") {
            // Email input field
            TextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password input field
            TextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Submit button
            Button (
                onClick = {
                    // TODO: Implement login logic here.
                    println("Attempting to log in with Email: $email, Password: $password")
                    viewModel.login(email.value, password.value)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }

        } else {
//            TODO: Refactor this to a automatic redirection to home
            Text(text = "Login successful!")

            Container(title = "Current token value") {
                Text(text = authToken.value)
            }

            Button(onClick = onSuccessfulLogin) { Text(text = "Go to home") }
        }
    }
}