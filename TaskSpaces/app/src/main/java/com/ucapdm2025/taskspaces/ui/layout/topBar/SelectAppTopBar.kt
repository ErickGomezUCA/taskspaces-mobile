package com.ucapdm2025.taskspaces.ui.layout.topBar

import androidx.compose.runtime.Composable

@Composable
fun SelectAppTopBar(currentRoute: String) {
    when (currentRoute) {
        "HomeRoute" -> {

        }

        else -> {
            AppTopBar(title = "Top App Bar")
        }
    }
}