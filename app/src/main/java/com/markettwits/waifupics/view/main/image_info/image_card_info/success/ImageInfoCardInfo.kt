package com.markettwits.waifupics.view.main.image_info.image_card_info.success

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.markettwits.waifupics.ui.theme.DirtyWhite
import com.markettwits.waifupics.ui.theme.WaifuPicsTheme
import com.markettwits.waifupics.view.main.image_info.artist.success.ArtistInfo
import com.markettwits.waifupics.view.main.image_info.color_palette.success.ColorPalette

@Preview
@Composable
fun ImageInfoCardPreview(){
    WaifuPicsTheme {
        ImageInfoCard()
    }
}

@Composable
fun ImageInfoCard(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(20.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        ArtistInfo()
        Spacer(
            modifier = Modifier
                .height(0.2.dp)
                .fillMaxWidth()
                .background(DirtyWhite)
        )
        SourceAndResource()
        Spacer(
            modifier = Modifier
                .height(0.2.dp)
                .fillMaxWidth()
                .background(DirtyWhite)
        )
        ColorPalette()
    }
}
