package com.ucapdm2025.taskspaces.ui.components.workspace

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.ucapdm2025.taskspaces.ui.components.general.UserAvatar
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

@Composable
fun UserCard(username: String, avatar: String, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserAvatar(avatar = avatar, size = 70)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = username,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

    }
}
/**
 * A preview composable for the [UserCard] component.
 *
 * Displays a mock user profile card in both light and dark UI modes
 * to validate layout, alignment, and styling consistency.
 */
@Preview(showBackground = true)
@Composable
fun UserCardPreviewLight() {
    TaskSpacesTheme(darkTheme = false) {
        ExtendedColors(darkTheme = false) {
            Column(
                modifier = Modifier
                    .background(Color.Gray)
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                UserCard(username = "Sample User", avatar = "https://avatars.githubusercontent.com/u/90425287?v=4")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserCardPreviewDark() {
    TaskSpacesTheme(darkTheme = true) {
        ExtendedColors(darkTheme = true) {
            Column(
                modifier = Modifier
                    .background(Color.DarkGray)
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                UserCard(username = "Sample User", avatar = "https://avatars.githubusercontent.com/u/90425287?v=4")
            }
        }
    }
}
