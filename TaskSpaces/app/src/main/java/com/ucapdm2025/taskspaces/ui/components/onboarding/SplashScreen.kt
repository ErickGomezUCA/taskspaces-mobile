package com.ucapdm2025.taskspaces.ui.components.onboarding

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavHostController
import com.ucapdm2025.taskspaces.R
import com.ucapdm2025.taskspaces.ui.navigation.LoginRoute
import com.ucapdm2025.taskspaces.ui.navigation.OnboardingRoute
import com.ucapdm2025.taskspaces.ui.navigation.SplashRoute
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private val Context.dataStore by preferencesDataStore(name = "settings")
private val KEY_FIRST_TIME = booleanPreferencesKey("first_time")

@Composable
fun SplashScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ExtendedTheme.colors.onBoarding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_white),
            contentDescription = "Logo",
            modifier = Modifier.size(90.dp)
        )
    }
}

@Composable
fun SplashScreen(navController: NavHostController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        delay(1500)

        val isFirstTime = context.dataStore.data
            .map { it[KEY_FIRST_TIME] ?: true }
            .first()

        if (isFirstTime) {
            scope.launch {
                context.dataStore.edit { prefs ->
                    prefs[KEY_FIRST_TIME] = false
                }
            }
            navController.navigate(OnboardingRoute) {
                popUpTo(SplashRoute) { inclusive = true }
                launchSingleTop = true
            }
        } else {
            navController.navigate(LoginRoute) {
                popUpTo(SplashRoute) { inclusive = true }
                launchSingleTop = true
            }
        }
    }
    SplashScreenContent()
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
            SplashScreenContent()
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
            SplashScreenContent()
        }
    }
}
