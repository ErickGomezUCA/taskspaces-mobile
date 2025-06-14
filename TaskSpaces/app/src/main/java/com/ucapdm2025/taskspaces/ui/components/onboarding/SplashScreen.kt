package com.ucapdm2025.taskspaces.ui.components.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ucapdm2025.taskspaces.R
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

/**
 * A composable function that displays the splash screen of the app.
 *
 * This screen shows the TaskSpaces logo centered on a background color
 * defined for the onboarding theme. It is typically shown while the app is loading.
 */
@Composable
fun SplashScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ExtendedTheme.colors.onBoarding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(90.dp)
        )
    }
}

/**
 * A preview composable for the [SplashScreen] using the light theme.
 *
 * Allows developers to visualize the splash screen appearance in light mode.
 */
@Preview(showBackground = true)
@Composable
fun SplashScreenPreviewLight() {
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            SplashScreen()
        }
    }
}

/**
 * A preview composable for the [SplashScreen] using the dark theme.
 *
 * Allows developers to visualize the splash screen appearance in dark mode.
 */
@Preview(showBackground = true)
@Composable
fun SplashScreenPreviewDark() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            SplashScreen()
        }
    }
}
