package com.ucapdm2025.taskspaces.ui.screens.test.login

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucapdm2025.taskspaces.ui.screens.login.LoginViewModel

@Composable
fun TestLoginScreen(
    viewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory)
) {
}