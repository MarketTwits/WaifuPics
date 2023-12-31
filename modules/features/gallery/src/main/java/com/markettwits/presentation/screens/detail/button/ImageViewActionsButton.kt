package com.markettwits.presentation.screens.detail.button

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.markettwits.gallery.R

@Composable
fun ShareButton(
    followTheme: Boolean = false,
    onItemClick: () -> Unit
) {
    BottomBarColumn(
        imageVector = Icons.Outlined.Share,
        followTheme = followTheme,
        title = stringResource(R.string.share)
    ) {
        onItemClick()
    }
}


@Composable
 fun EditButton(
    followTheme: Boolean = false,
    onItemClick: () -> Unit
) {
    BottomBarColumn(
        imageVector = Icons.Outlined.Edit,
        followTheme = followTheme,
        title = stringResource(R.string.edit)
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
        title = stringResource(R.string.use_as)
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
        title = stringResource(R.string.save_to_gallery)
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
        title = stringResource(R.string.delete)
    ) {
        onItemClick()
    }
}

