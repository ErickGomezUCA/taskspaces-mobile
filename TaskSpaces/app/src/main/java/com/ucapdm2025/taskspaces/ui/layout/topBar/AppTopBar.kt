package com.ucapdm2025.taskspaces.ui.layout.topBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ucapdm2025.taskspaces.ui.navigation.UserRoute
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme
import com.ucapdm2025.taskspaces.utils.getTopAppBarContainerColor
import com.ucapdm2025.taskspaces.utils.getTopAppBarContentColor

/**
 * Top bar for the app, used in all screens.
 * @param title The title to show in the top bar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    navController: NavHostController,
    variant: AppTopBarVariant = AppTopBarVariant.DEFAULT
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = getTopAppBarContainerColor(variant),
            titleContentColor = getTopAppBarContentColor(variant),
        ),
        title = { Text(text = title) },
        navigationIcon = {
            if (variant == AppTopBarVariant.NAVIGATION) {
                IconButton(onClick = { /* TODO: Handle back navigation */ }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Back",
                        tint = getTopAppBarContentColor(variant)
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = { navController.navigate(UserRoute) }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "User",
                    tint = getTopAppBarContentColor(variant)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AppTopBarDefaultLightPreview() {
    TaskSpacesTheme {
        AppTopBar(title = "Title example", navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun AppTopBarHomeLightPreview() {
    TaskSpacesTheme {
        AppTopBar(
            title = "Title example",
            navController = rememberNavController(),
            variant = AppTopBarVariant.HOME
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppTopBarNavigationLightPreview() {
    TaskSpacesTheme {
        AppTopBar(
            title = "Title example",
            navController = rememberNavController(),
            variant = AppTopBarVariant.NAVIGATION
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppTopBarDefaultDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        AppTopBar(title = "Title example", navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun AppTopBarHomeDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        AppTopBar(
            title = "Title example",
            navController = rememberNavController(),
            variant = AppTopBarVariant.HOME
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppTopBarNavigationDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        AppTopBar(
            title = "Title example",
            navController = rememberNavController(),
            variant = AppTopBarVariant.NAVIGATION
        )
    }
}