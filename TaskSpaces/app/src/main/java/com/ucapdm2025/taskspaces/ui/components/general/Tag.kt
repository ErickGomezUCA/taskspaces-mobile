package com.ucapdm2025.taskspaces.ui.components.general

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme

/**
 * Data class representing a tag or label associated with a task.
 *
 * @property title The text displayed inside the tag.
 * @property color The color used for the tag's border and text.
 */
data class Tag(
    val title: String,
    val color: Color
)

/**
 * A composable function that displays a styled tag with a title and color.
 *
 * This component is typically used to visually represent task categories or labels.
 *
 * @param tag The [Tag] object containing the title and color to be displayed.
 */
@Composable
fun Tag(tag: Tag) {
    Row(
        modifier = Modifier
            .border(1.dp, tag.color, RoundedCornerShape(8.dp))
            .background(ExtendedTheme.colors.tag, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = tag.title,
            color = tag.color,
            fontSize = 12.sp,
        )
    }
}
