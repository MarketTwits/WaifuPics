package com.markettwits.presentation.detail.bottomBar

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

@Composable
fun DownBarScreenImage(modifier: Modifier, onClickDelete: () -> Unit) {
    Row(
        modifier = modifier
            .padding(20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    )
    {
        Icon(
            modifier = Modifier.size(30.dp),
            imageVector = Icons.Default.Share,
            contentDescription = "share",
            tint = MaterialTheme.colorScheme.onBackground
        )
        Icon(
            modifier = Modifier
                .size(30.dp)
                .clickable { onClickDelete() },
            imageVector = Icons.Default.Delete,
            contentDescription = "delete",
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}