package com.markettwits.waifupics.random.components.image_info.image_card_info.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.markettwits.theme.components.DirtyWhite
import com.markettwits.theme.components.Shapes
import com.markettwits.waifupics.random.components.image_info.artist.ArtistInfoLoading
import com.markettwits.waifupics.random.components.image_info.color_palette.ColorPaletteLoading


@Composable
fun ImageCardInfoLoading(){
    Column(
        modifier = Modifier
            .padding(20.dp)
            .clip(Shapes.medium)
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        ArtistInfoLoading()
        HorizontalDivider(thickness = 0.1.dp, color = DirtyWhite)
        ImageParametersLoading()
        HorizontalDivider(thickness = 0.1.dp, color = DirtyWhite)
        ColorPaletteLoading()
    }
}