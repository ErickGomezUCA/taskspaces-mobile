package com.ucapdm2025.taskspaces.ui.layout

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
//TODO: Determine a way to show go back icon ir hide it
fun AppTopBar(title: String) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        title = { Text(text = title) },
        navigationIcon = {
//            TODO: Define go back to stack
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Go back",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        actions = {
//            TODO: Go to user page
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "User",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AppTopBarPreview() {
    TaskSpacesTheme {
        AppTopBar(title = "Title example")
    }
}