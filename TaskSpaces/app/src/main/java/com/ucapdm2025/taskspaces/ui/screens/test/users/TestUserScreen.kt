package com.ucapdm2025.taskspaces.ui.screens.test.users

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
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
import com.ucapdm2025.taskspaces.data.model.UserModel
import com.ucapdm2025.taskspaces.ui.components.general.Container
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

// Do not follow this code for production use, it's just a test screen to demonstrate the usage of ViewModel and StateFlow in Jetpack Compose.
private data class MutableUser(
    var fullname: MutableState<String>,
    var username: MutableState<String>,
    var email: MutableState<String>,
    var avatar: MutableState<String>,
)

/** * A composable function that displays a test user screen.
 * It allows you to perform CRUD operations on users using a ViewModel.
 *
 * @param viewModel The ViewModel instance for managing user data.
 */
@Composable
fun TestUserScreen(
    viewModel: TestUserViewModel = viewModel() // Assuming UsersViewModel is defined elsewhere
) {
//    Test viewModel here
    val users =
        viewModel.users.collectAsStateWithLifecycle() // Assuming users is a StateFlow<List<User>>

//    Other states:
//    1. Get user by ID
    val selectedUserId: MutableState<String> = remember { mutableStateOf("") }
    val userModel: MutableState<UserModel?> = remember { mutableStateOf(null) }

//    2. Create user
    val mutableUserInfo: MutableState<MutableUser> = remember {
        mutableStateOf(
            MutableUser(
                fullname = mutableStateOf(""),
                username = mutableStateOf(""),
                email = mutableStateOf(""),
                avatar = mutableStateOf(""),
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "UserRepository example", fontWeight = FontWeight.Medium, fontSize = 20.sp)

        Container(title = "- All users") {
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

        Container(title = "- Get user by ID") {
            TextField(
                value = selectedUserId.value,
                onValueChange = { selectedUserId.value = it },
                placeholder = { Text(text = "Search user by ID") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    userModel.value = viewModel.getUserById(selectedUserId.value.toInt())
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Search")
            }

            if (userModel.value != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = "id: ${userModel.value!!.id}")
                    Text(text = "fullname: ${userModel.value!!.fullname}")
                    Text(text = "username: ${userModel.value!!.username}")
                    Text(text = "email: ${userModel.value!!.email}")
                }
            } else {
                Text(text = "User not found")
            }
        }

        Container(title = "- Create user") {
            TextField(
                value = mutableUserInfo.value.fullname.value,
                onValueChange = { mutableUserInfo.value.fullname.value = it },
                placeholder = { Text(text = "Fullname") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = mutableUserInfo.value.username.value,
                onValueChange = { mutableUserInfo.value.username.value = it },
                placeholder = { Text(text = "Username") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = mutableUserInfo.value.email.value,
                onValueChange = { mutableUserInfo.value.email.value = it },
                placeholder = { Text(text = "Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.createUser(
                        fullname = mutableUserInfo.value.fullname.value,
                        username = mutableUserInfo.value.username.value,
                        email = mutableUserInfo.value.email.value,
                        avatar = mutableUserInfo.value.avatar.value
                    )

                    mutableUserInfo.value = MutableUser(
                        fullname = mutableStateOf(""),
                        username = mutableStateOf(""),
                        email = mutableStateOf(""),
                        avatar = mutableStateOf(""),
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Create")
            }
        }

        Container(title = "- Update user") {
            TextField(
                value = selectedUserId.value,
                onValueChange = { selectedUserId.value = it },
                placeholder = { Text(text = "ID") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = mutableUserInfo.value.fullname.value,
                onValueChange = { mutableUserInfo.value.fullname.value = it },
                placeholder = { Text(text = "Fullname") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = mutableUserInfo.value.username.value,
                onValueChange = { mutableUserInfo.value.username.value = it },
                placeholder = { Text(text = "Username") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = mutableUserInfo.value.email.value,
                onValueChange = { mutableUserInfo.value.email.value = it },
                placeholder = { Text(text = "Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.updateUser(
                        id = selectedUserId.value.toInt(),
                        fullname = mutableUserInfo.value.fullname.value,
                        username = mutableUserInfo.value.username.value,
                        email = mutableUserInfo.value.email.value,
                        avatar = mutableUserInfo.value.avatar.value
                    )

                    selectedUserId.value = ""

                    mutableUserInfo.value = MutableUser(
                        fullname = mutableStateOf(""),
                        username = mutableStateOf(""),
                        email = mutableStateOf(""),
                        avatar = mutableStateOf(""),
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Update")
            }
        }

        Container(title = "- Delete user") {
            TextField(
                value = selectedUserId.value,
                onValueChange = { selectedUserId.value = it },
                placeholder = { Text(text = "ID") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.deleteUser(selectedUserId.value.toInt())

                    selectedUserId.value = ""
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Delete")
            }
        }

    }
}

/**
 * Preview functions for the TestUserScreen composable. These functions allow you to see how the screen looks in both light and dark themes.**
 */
@Preview(showBackground = true)
@Composable
fun TestUserScreenLightPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            Scaffold { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding)) {
                    TestUserScreen()
                }
            }
        }
    }
}

/**
 * Preview function for the TestUserScreen composable in dark mode. This function allows you to see how the screen looks in dark theme.
 */
@Preview(showBackground = true)
@Composable
fun TestUserScreenDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            Scaffold { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding)) {
                    TestUserScreen()
                }
            }
        }
    }
}