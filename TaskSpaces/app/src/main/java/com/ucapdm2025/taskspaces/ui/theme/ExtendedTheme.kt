package com.ucapdm2025.taskspaces.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


// 1. Define color structure
@Immutable
data class ExtendedColors(
    val background05: Color,
    val background10: Color,
    val background25: Color,
    val background50: Color,
    val background75: Color,
    val background100: Color,
    val onBackground05: Color,
    val onBackground10: Color,
    val onBackground25: Color,
    val onBackground50: Color,
    val onBackground75: Color,
    val onBoarding: Color,
    val primary100: Color,
    val primary50: Color,
    val primary25: Color,
    val projectColumn: Color,
    val tag: Color,
    val projectBackground: Color,
    val cardContent: Color,
)

// 2. Set colors for light and dark theme (must follow ExtendedColors structure)
val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        background05 = Color.Unspecified,
        background10 = Color.Unspecified,
        background25 = Color.Unspecified,
        background50 = Color.Unspecified,
        background75 = Color.Unspecified,
        background100 = Color.Unspecified,
        onBackground05 = Color.Unspecified,
        onBackground10 = Color.Unspecified,
        onBackground25 = Color.Unspecified,
        onBackground50 = Color.Unspecified,
        onBackground75 = Color.Unspecified,
        onBoarding = Color.Unspecified,
        primary100 = Color.Unspecified,
        primary50 = Color.Unspecified,
        primary25 = Color.Unspecified,
        projectColumn = Color.Unspecified,
        tag = Color.Unspecified,
        projectBackground = Color.Unspecified,
        cardContent = Color.Unspecified,
    )
}

val localExtendedColorsDark = staticCompositionLocalOf {
    ExtendedColors(
        background05 = White05,
        background10 = White10,
        background25 = White25,
        background50 = White50,
        background75 = White75,
        background100 = White100,
        onBackground05 = White05,
        onBackground10 = White10,
        onBackground25 = White25,
        onBackground50 = White50,
        onBackground75 = White75,
        onBoarding = PrimaryLight100,
        primary100 = PrimaryDark100,
        primary50 = PrimaryDark50,
        primary25 = PrimaryDark25,
        projectColumn = ProjectScreenColumnDark,
        tag = Black75,
        projectBackground = ProjectScreenBackgroundDark,
        cardContent = CardContentDark,
    )
}

val localExtendedColorsLight = staticCompositionLocalOf {
    ExtendedColors(
        background05 = Black05,
        background10 = Black10,
        background25 = Black25,
        background50 = Black50,
        background75 = Black75,
        background100 = Black100,
        onBackground05 = Black05,
        onBackground10 = Black10,
        onBackground25 = Black25,
        onBackground50 = Black50,
        onBackground75 = Black75,
        onBoarding = PrimaryLight100,
        primary100 = PrimaryLight100,
        primary50 = PrimaryLight50,
        primary25 = PrimaryLight25,
        projectColumn = ProjectScreenColumnLight,
        tag = White75,
        projectBackground = ProjectScreenBackgroundLight,
        cardContent = CardContentLight,
    )
}

// 3. Create a Composable function to provide the colors, specifying dark or light theme (like TaskSpacesTheme)
@Composable
fun ExtendedColors(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val extendedColors = if (darkTheme) {
        localExtendedColorsDark.current
    } else {
        localExtendedColorsLight.current
    }

    CompositionLocalProvider(
        LocalExtendedColors provides extendedColors
    ) {
        MaterialTheme(
            content = content
        )
    }
}

// 4. Create object to call the colors (like MaterialTheme.colorScheme)
// Light or dark theme will be automatically selected based on the system theme (or ExtendedColors component)
// Usage example: ExtendedTheme.colors.background05
object ExtendedTheme {
    val colors: ExtendedColors
        @Composable
        get() = LocalExtendedColors.current
}