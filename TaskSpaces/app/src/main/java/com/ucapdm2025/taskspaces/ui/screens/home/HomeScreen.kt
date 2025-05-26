package com.ucapdm2025.taskspaces.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucapdm2025.taskspaces.data.model.Workspace
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

// TODO: Implement the HomeScreen UI
/**
 * A composable function that displays the home screen.
 * Currently, it shows a simple text message.
 *
 * @param modifier A [Modifier] for customizing the layout or behavior of the home screen.
 */
@Composable
fun HomeScreen(
//    viewModel: HomeViewModel = viewModel()
) {
//    Consume viewModel states here
//    val workspaces = viewModel.workspaces.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        Text("Home Screen", color = MaterialTheme.colorScheme.onBackground)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenLightPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            HomeScreen()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun HomeScreenDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            HomeScreen()
        }
    }
}