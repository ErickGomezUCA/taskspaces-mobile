package com.ucapdm2025.taskspaces.utils

import androidx.navigation.NavBackStackEntry

fun getCurrentRoute(navBackStackEntry: NavBackStackEntry?): String? {
    return navBackStackEntry?.destination?.route?.split(".")?.last()
}