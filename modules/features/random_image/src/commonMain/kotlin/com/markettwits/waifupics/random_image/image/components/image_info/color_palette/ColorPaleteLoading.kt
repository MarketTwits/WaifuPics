package com.markettwits.waifupics.view.main.ui.image_info.color_palette.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.markettwits.waifupics.random_image.image.components.image_state.loading.components.LoadingBox

@Composable
fun ColorPaletteLoading(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column( modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LoadingBox(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .width(110.dp)
                        .align(Alignment.CenterVertically)
                )
                LoadingBox(
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .width(60.dp)
                        .align(Alignment.CenterVertically)
                )
            }
            Row {
                LoadingBox(modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth())
            }
        }
    }
}