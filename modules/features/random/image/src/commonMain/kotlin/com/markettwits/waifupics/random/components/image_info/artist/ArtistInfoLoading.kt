package com.markettwits.waifupics.random.components.image_info.artist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.markettwits.theme.components.Shapes
import com.markettwits.waifupics.random.components.image_state.loading.components.LoadingBox


@Composable
fun ArtistTitleLoading(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(Shapes.medium)
                .size(20.dp)
                .background(MaterialTheme.colorScheme.primaryContainer),
        )
        Spacer(modifier = Modifier.width(10.dp))
        LoadingBox()
    }
}
@Composable
fun ArtistInfoLoading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column( modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
        ) {
            Row {
                ArtistTitleLoading()
            }
            Row {
                LoadingBox(modifier = Modifier
                    .padding(4.dp)
                    .width(100.dp))
                LoadingBox(modifier = Modifier
                    .padding(4.dp)
                    .width(50.dp))
            }
            Row {
                LoadingBox(modifier = Modifier
                    .padding(4.dp)
                    .width(80.dp))
                LoadingBox(modifier = Modifier
                    .padding(4.dp)
                    .width(40.dp))
            }
        }
    }
}