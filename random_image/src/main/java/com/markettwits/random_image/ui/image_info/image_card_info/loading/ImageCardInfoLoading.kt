package com.markettwits.waifupics.view.main.ui.image_info.image_card_info.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.markettwits.random_image.ui.image_info.artist.loading.ArtistInfoLoading
import com.markettwits.waifupics.theame.theme.DirtyWhite
import com.markettwits.waifupics.theame.theme.WaifuPicsTheme
import com.markettwits.waifupics.view.main.ui.image_info.color_palette.loading.ColorPaletteLoading


@Composable
fun ImageCardInfoLoading(){
    Column(
        modifier = Modifier
            .padding(20.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        ArtistInfoLoading()
        Divider(thickness = 0.1.dp, color = DirtyWhite)
        ImageParametersLoading()
        Divider(thickness = 0.1.dp, color = DirtyWhite)
        ColorPaletteLoading()
    }
}
@Preview
@Composable
private fun ImageCardInfoLoadingPreview(){
    WaifuPicsTheme {
        ImageCardInfoLoading()
    }
}