package com.ucapdm2025.taskspaces.ui.components.general

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ucapdm2025.taskspaces.ui.theme.ExtendedColors
import com.ucapdm2025.taskspaces.ui.theme.ExtendedTheme
import com.ucapdm2025.taskspaces.ui.theme.TaskSpacesTheme

/**
 * UserAvatar is a composable function that displays a user's avatar.
 *
 * @param avatar The URL of the user's avatar image. If null or empty, a default icon is shown.
 * @param modifier Modifier to customize the appearance of the avatar.
 * @param size The size of the avatar in dp. Default is 36dp.
 */
@Composable
fun UserAvatar(
    avatar: String?,
    modifier: Modifier = Modifier,
    size: Int = 36,
) {
    if (!avatar.isNullOrEmpty()) {
        AsyncImage(
            model = avatar,
            contentDescription = "User avatar",
            modifier = Modifier
                .size(size.dp)
                .clip(CircleShape)
                .background(ExtendedTheme.colors.primary25, CircleShape)
        )
    } else {
        Image(
            painter = painterResource(id = android.R.drawable.ic_menu_camera),
            contentDescription = "User avatar",
            modifier = Modifier
                .size(size.dp)
                .background(ExtendedTheme.colors.primary50, CircleShape)
        )
    }
}

/**
 * Previews for UserAvatar composable function.
 * These previews show how the UserAvatar looks with and without an avatar URL.
 */
@Preview
@Composable
fun UserAvatarNullPreview() {
    TaskSpacesTheme {
        ExtendedColors {
            UserAvatar(
                avatar = null,
                modifier = Modifier.size(36.dp),
                size = 36
            )
        }
    }
}

/**
 * Preview for UserAvatar with a valid avatar URL.
 * This shows how the UserAvatar looks when an avatar URL is provided.
 */
@Preview
@Composable
fun UserAvatarWithValuePreview() {
    TaskSpacesTheme {
        ExtendedColors {
            UserAvatar(
                avatar = "https://avatars.githubusercontent.com/u/90425287?v=4",
                modifier = Modifier.size(36.dp),
                size = 36
            )
        }
    }
}