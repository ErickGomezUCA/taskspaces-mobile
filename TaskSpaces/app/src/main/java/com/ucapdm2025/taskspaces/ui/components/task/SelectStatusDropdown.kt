package com.ucapdm2025.taskspaces.ui.components.task

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ucapdm2025.taskspaces.ui.components.projects.StatusVariations
import com.ucapdm2025.taskspaces.ui.components.projects.TaskStatus

/**
 * List of status variations available for selection in the dropdown menu.
 * Each status corresponds to a different task state.
 */
val options = listOf(
    StatusVariations.PENDING,
    StatusVariations.DOING,
    StatusVariations.DONE
)

/**
 * A composable function that displays a dropdown menu for selecting task status.
 *
 * @param value The currently selected status variation, defaulting to PENDING.
 * @param onValueChange Callback function to handle changes in the selected status.
 */
@Composable
fun SelectStatusDropdown(
    value: StatusVariations = StatusVariations.PENDING,
    onValueChange: (StatusVariations) -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedStatus by remember { mutableStateOf(value) }


    Box {
        Box(Modifier.clickable{ expanded = !expanded }) {
            TaskStatus(status = selectedStatus)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { status ->
                DropdownMenuItem(
                    text = { TaskStatus(status = status) },
                    onClick = {
                        onValueChange(status)
                        selectedStatus = status
                        expanded = false // Close the menu after selection
                    }
                )
            }
        }
    }
}

/**
 * Preview composable for the [SelectStatusDropdown] component.
 */
@Preview(showBackground = true)
@Composable
fun DropdownMenuPreview() {
    SelectStatusDropdown()
}