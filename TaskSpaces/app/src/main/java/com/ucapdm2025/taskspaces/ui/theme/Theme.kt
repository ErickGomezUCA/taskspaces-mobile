package com.ucapdm2025.taskspaces.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark100,
    secondary = PrimaryDark75,
    tertiary = PrimaryDarkActive,
    background = DarkBackground,
    surface = DarkBackground,
    onPrimary = Black100,
    onSecondary = Black100,
    onTertiary = Black100,
    onBackground = White100,
    onSurface = White100,
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight100,
    secondary = PrimaryLight75,
    tertiary = PrimaryLightActive,
    background = LightBackground,
    surface = LightBackground,
    onPrimary = White100,
    onSecondary = White100,
    onTertiary = White100,
    onBackground = Black100,
    onSurface = Black100,
)


@Composable
fun TaskSpacesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = OutfitTypography,
        content = content
    )
}