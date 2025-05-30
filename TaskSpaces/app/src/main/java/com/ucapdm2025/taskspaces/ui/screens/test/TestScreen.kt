package com.ucapdm2025.taskspaces.ui.screens.test

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

@Composable
fun TestScreen(
    viewModel: TestViewModel = viewModel() // Assuming UsersViewModel is defined elsewhere
) {
//    Test viewModel here
    val users =
        viewModel.users.collectAsStateWithLifecycle() // Assuming users is a StateFlow<List<User>>

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "UserRepository example", fontWeight = FontWeight.Medium, fontSize = 20.sp)

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "- All users")
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                users.value.forEach { item ->
                    Card(modifier = Modifier
                        .fillMaxWidth()) {
                        Text(text = "id: ${item.id}")
                        Text(text = "fullname: ${item.fullname}")
                        Text(text = "username: ${item.username}")
                        Text(text = "email: ${item.email}")
                    }
                }
            }
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "- Get user by ID")


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