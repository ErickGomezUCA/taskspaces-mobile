package com.ucapdm2025.taskspaces.ui.screens.test

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucapdm2025.taskspaces.data.model.User
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

// Do not follow this code for production use, it's just a test screen to demonstrate the usage of ViewModel and StateFlow in Jetpack Compose.
data class NewUser(
    val id: Int,
    var fullname: MutableState<String>,
    var username: MutableState<String>,
    var email: MutableState<String>,
    var avatar: String,
    var createdAt: String,
    var updatedAt: String
)

@Composable
fun TestScreen(
    viewModel: TestViewModel = viewModel() // Assuming UsersViewModel is defined elsewhere
) {
//    Test viewModel here
    val users =
        viewModel.users.collectAsStateWithLifecycle() // Assuming users is a StateFlow<List<User>>

//    Other states:
//    1. Get user by ID
    val searchUserById: MutableState<String> = remember { mutableStateOf("") }
    val user: MutableState<User?> = remember { mutableStateOf(null) }

//    2. Create user
    val autoIncrementId = remember { mutableStateOf(users.value.size + 1) } // For auto-incrementing ID
    val newUserInfo: MutableState<NewUser> = remember {
        mutableStateOf(NewUser(id = autoIncrementId.value, fullname = mutableStateOf(""), username = mutableStateOf(""), email = mutableStateOf(""), avatar = "", createdAt = "", updatedAt = ""))
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "UserRepository example", fontWeight = FontWeight.Medium, fontSize = 20.sp)

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "- All users")
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                users.value.forEach { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
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

            TextField(
                value = searchUserById.value,
                onValueChange = { searchUserById.value = it },
                placeholder = { Text(text = "Search user by ID") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    user.value = viewModel.getUserById(searchUserById.value.toInt())
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Search")
            }

            if (user.value != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = "id: ${user.value!!.id}")
                    Text(text = "fullname: ${user.value!!.fullname}")
                    Text(text = "username: ${user.value!!.username}")
                    Text(text = "email: ${user.value!!.email}")
                }
            } else {
                Text(text = "User not found")
            }
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "- Create user")

            TextField(
                value = newUserInfo.value.fullname.value,
                onValueChange = { newUserInfo.value.fullname.value = it },
                placeholder = { Text(text = "Fullname") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = newUserInfo.value.username.value,
                onValueChange = { newUserInfo.value.username.value = it },
                placeholder = { Text(text = "Username") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = newUserInfo.value.email.value,
                onValueChange = { newUserInfo.value.email.value = it },
                placeholder = { Text(text = "Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val createUserInfo = User(
                        id = autoIncrementId.value,
                        fullname = newUserInfo.value.fullname.value,
                        username = newUserInfo.value.username.value,
                        email = newUserInfo.value.email.value,
                        avatar = newUserInfo.value.avatar,
                        createdAt = newUserInfo.value.createdAt,
                        updatedAt = newUserInfo.value.updatedAt
                    )

                    viewModel.createUser(createUserInfo)

                    newUserInfo.value = NewUser(
                        id = ++autoIncrementId.value,
                        fullname = mutableStateOf(""),
                        username = mutableStateOf(""),
                        email = mutableStateOf(""),
                        avatar = "",
                        createdAt = "",
                        updatedAt = ""
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Search")
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