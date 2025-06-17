package com.ucapdm2025.taskspaces.ui.components.general

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Data class representing an option in a dropdown menu.
 *
 * @property label The text label for the menu option.
 * @property icon An optional composable icon to display with the option.
 * @property onClick The action to perform when the option is selected.
 */
data class DropdownMenuOption(
    val label: String,
    val icon: @Composable (() -> Unit)? = null,
    val onClick: () -> Unit
)


/**
 * Composable function that displays a dropdown menu with several sections and options.
 * Each option can have a leading or trailing icon and performs an action when selected.
 */
@Composable
fun DropdownMenu(
    options: List<DropdownMenuOption> = emptyList()
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = !expanded }, modifier = Modifier.padding(1.dp)) {
            Icon(Icons.Default.MoreHoriz, contentDescription = "More options")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.label) },
                    leadingIcon = option.icon,
                    onClick = {
                        option.onClick()
                        expanded = false // Close the menu after selection
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DropdownMenuPreview() {
    DropdownMenu(
        options = listOf(
            DropdownMenuOption(
                label = "Option 1",
                icon = { Icon(Icons.Default.MoreVert, contentDescription = "Option 1 Icon") },
                onClick = { /* Handle Option 1 click */ }
            ),
            DropdownMenuOption(
                label = "Option 2",
                onClick = { /* Handle Option 2 click */ }
            ),
            DropdownMenuOption(
                label = "Option 3",
                icon = { Icon(Icons.Default.MoreVert, contentDescription = "Option 3 Icon") },
                onClick = { /* Handle Option 3 click */ }
            )
        )
    )
}