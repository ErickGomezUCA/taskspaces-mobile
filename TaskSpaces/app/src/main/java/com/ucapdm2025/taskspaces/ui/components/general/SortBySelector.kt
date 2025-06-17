package com.ucapdm2025.taskspaces.ui.components.general

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

/**
 * Represents the sorting options available in the Bookmarks screen.
 */
enum class SortOption(val label: String) {
    NAME("Name"),
    PROJECT("Project"),
    WORKSPACE("Workspace")
}

/**
 * A composable that displays a "Sort by" dropdown selector.
 *
 * This component allows the user to choose how to sort bookmarked tasks
 * (e.g., by name, date, or priority). It is intended to appear above
 * the task list in the bookmarks screen.
 *
 * @param selected The currently selected sort option.
 * @param onSelect A lambda that receives the newly selected sort option.
 */
@Composable
fun SortBySelector(
    selected: SortOption = SortOption.NAME,
    onSelect: (SortOption) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .padding(start = 0.dp, end = 24.dp, top = 8.dp, bottom = 8.dp),
                 verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Sort by:", modifier = Modifier.padding(end = 8.dp),
                style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground)
            Text(text = selected.label, color = MaterialTheme.colorScheme.primary,style = MaterialTheme.typography.bodyMedium)
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown",
                tint = MaterialTheme.colorScheme.primary)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false },
            modifier = Modifier.padding(horizontal = 6.dp)) {
            SortOption.values().forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.label,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium) },
                    onClick = {
                        onSelect(option)
                        expanded = false
                    }
                )
            }
        }
    }
}


/**
 * Preview of the SortBySelector component in light mode.
 */
@Preview(showBackground = true)
@Composable
fun SortBySelectorLightPreview() {
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            SortBySelector(
                selected = SortOption.NAME,
                onSelect = {}
            )
        }
    }
}

/**
 * Preview of the SortBySelector component in dark mode.
 */
@Preview(showBackground = true, backgroundColor = 0xFF27272A)
@Composable
fun SortBySelectorDarkPreview() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            SortBySelector(
                selected = SortOption.NAME,
                onSelect = {}
            )
        }
    }
}