package com.markettwits.waifupics.view.main.ui.image_info.image_card_info.success

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
import com.markettwits.waifupics.ui.theme.DirtyWhite
import com.markettwits.waifupics.ui.theme.WaifuPicsTheme
import com.markettwits.waifupics.view.main.data.net.models.RandomImageCloud
import com.markettwits.waifupics.view.main.ui.image_info.artist.success.ArtistInfo
import com.markettwits.waifupics.view.main.ui.image_info.color_palette.success.ColorPalette
@Preview
@Composable
fun ImageInfoCardPreview(){
    WaifuPicsTheme {
       // ImageInfoCard(fakeHex())
    }
}

@Composable
fun ImageInfoCard(
    state : RandomImageCloud,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(20.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        ArtistInfo()
        Divider(thickness = 0.1.dp, color = DirtyWhite)
        SourceAndResource(state.data.attributes)
        Divider(thickness = 0.1.dp, color = DirtyWhite)
        ColorPalette(state.data.attributes.colors.palette)
    }
}
