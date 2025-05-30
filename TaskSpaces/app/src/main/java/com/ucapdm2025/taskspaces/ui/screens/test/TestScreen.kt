package com.ucapdm2025.taskspaces.ui.screens.test

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

@Composable
fun TestScreen(
    viewModel: TestViewModel = viewModel() // Assuming UsersViewModel is defined elsewhere
) {
//    Test viewModel here
    val users = viewModel.users.collectAsStateWithLifecycle() // Assuming users is a StateFlow<List<User>>

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "UsersViewModel example")

        LazyColumn {
            items(users.value) { user ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "id: ${user.id}")
                    Text(text = "fullname: ${user.fullname}")
                    Text(text = "username: ${user.username}")
                    Text(text = "email: ${user.email}")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestScreenPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            TestScreen()
        }
    }
}