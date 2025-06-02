package com.ucapdm2025.taskspaces.ui.screens.test.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucapdm2025.taskspaces.ui.components.general.Container
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

@Composable
fun TestProjectScreen(
    viewModel: TestProjectViewModel = viewModel()
) {
    val project = viewModel.project.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "TestProjectViewModel example",
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        )

        Container(title = "- Workspace info", showOptionsButton = false) {
            Text(text = "ID: ${project.value?.id ?: "Loading..."}")
            Text(text = "Title: ${project.value?.title ?: "Loading..."}")
            Text(text = "Icon: ${project.value?.icon ?: "Loading..."}")
            Text(text = "Workspace ID: ${project.value?.workspaceId ?: "Loading..."}")
        }
    }
}

/**
 * Preview function for the TestProjectScreen composable in light mode. This function allows you to see how the screen looks in light theme.
 */
@Preview(showBackground = true)
@Composable
fun TestProjectScreenLightPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            Scaffold { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding)) {
                    TestProjectScreen()
                }
            }
        }
    }
}

/**
 * Preview function for the TestProjectScreen composable in dark mode. This function allows you to see how the screen looks in dark theme.
 */
@Preview(showBackground = true)
@Composable
fun TestProjectScreenDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            Scaffold { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding)) {
                    TestProjectScreen()
                }
            }
        }
    }
}