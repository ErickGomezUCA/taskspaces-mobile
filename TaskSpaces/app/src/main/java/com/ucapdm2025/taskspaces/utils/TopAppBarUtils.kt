package com.ucapdm2025.taskspaces.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ucapdm2025.taskspaces.ui.layout.topBar.AppTopBarVariant

/**
 * Returns the container color for the top app bar based on the given variant.
 *
 * @param variant The [AppTopBarVariant] specifying the style of the top app bar.
 * @return The [Color] to be used as the container color.
 */
@Composable
fun getTopAppBarContainerColor(variant: AppTopBarVariant): Color {
    return if (variant == AppTopBarVariant.HOME) {
        MaterialTheme.colorScheme.background
    } else {
        MaterialTheme.colorScheme.primary
    }
}

/**
 * Returns the content color for the top app bar based on the given variant.
 *
 * @param variant The [AppTopBarVariant] specifying the style of the top app bar.
 * @return The [Color] to be used as the content color.
 */
@Composable
fun getTopAppBarContentColor(variant: AppTopBarVariant): Color {
    return if (variant == AppTopBarVariant.HOME) {
        MaterialTheme.colorScheme.onBackground
    } else {
        MaterialTheme.colorScheme.onPrimary
    }
}