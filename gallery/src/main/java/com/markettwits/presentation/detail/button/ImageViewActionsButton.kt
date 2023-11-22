package com.markettwits.presentation.detail.button

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable

@Composable
fun ShareButton(
    followTheme: Boolean = false,
    onItemClick: () -> Unit
) {
    BottomBarColumn(
        imageVector = Icons.Outlined.Share,
        followTheme = followTheme,
        title = "Share"
    ) {
        onItemClick()
    }
}


@Composable
 fun EditButton(
    followTheme: Boolean = true,
    onItemClick: () -> Unit
) {
    BottomBarColumn(
        imageVector = Icons.Outlined.Edit,
        followTheme = followTheme,
        title = "Edit"
    ) {
        onItemClick()
    }
}

@Composable
 fun OpenAsButton(
    followTheme: Boolean = false,
    onItemClick: () -> Unit
) {
    BottomBarColumn(
        imageVector = Icons.Default.OpenInNew,
        followTheme = followTheme,
        title = "Use as"
    ) {
        onItemClick()
    }
}
@Composable
fun SaveButton(
    followTheme: Boolean = false,
    onItemClick: () -> Unit
) {
    BottomBarColumn(
        imageVector = Icons.Default.Save,
        followTheme = followTheme,
        title = "Save to gallery"
    ) {
        onItemClick()
    }
}

@Composable
fun DeleteButton(
    followTheme: Boolean = false,
    onItemClick: () -> Unit
) {
    BottomBarColumn(
        imageVector = Icons.Outlined.DeleteOutline,
        followTheme = followTheme,
        title = "Delete"
    ) {
        onItemClick()
    }
}

