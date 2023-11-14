package com.markettwits.presentation.detail.bottomBar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.markettwits.presentation.detail.GalleryScreenViewModel

@Composable
fun DownBarScreenImage(viewModel : GalleryScreenViewModel) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.4f))
            .padding(20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    )
    {
        Icon(
            modifier = Modifier
                .clickable { viewModel.shareImage() }
                .size(30.dp),
            imageVector = Icons.Default.Share,
            contentDescription = "share",
            tint = MaterialTheme.colorScheme.onBackground
        )
        Icon(
            modifier = Modifier
                .size(30.dp)
                .clickable { viewModel.delete() },
            imageVector = Icons.Default.Delete,
            contentDescription = "delete",
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}