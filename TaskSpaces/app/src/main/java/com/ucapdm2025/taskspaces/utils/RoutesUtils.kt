package com.ucapdm2025.taskspaces.utils

import androidx.navigation.NavBackStackEntry

/**
 * Utility function to get the current route from the NavBackStackEntry.
 *
 * @param navBackStackEntry The NavBackStackEntry from which to extract the current route.
 * @return The current route as a String, or null if not available.
 */
fun getCurrentRoute(navBackStackEntry: NavBackStackEntry?): String? {
    return navBackStackEntry?.destination?.route?.split(".")?.last()
}