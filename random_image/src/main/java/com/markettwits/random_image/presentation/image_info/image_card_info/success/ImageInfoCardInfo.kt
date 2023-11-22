package com.markettwits.random_image.presentation.image_info.image_card_info.success

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.markettwits.random_image.presentation.AuthorUi
import com.markettwits.random_image.presentation.ImageSourceUi
import com.markettwits.random_image.presentation.image_info.artist.success.ArtistInfo
import com.markettwits.waifupics.theame.theme.DirtyWhite
import com.markettwits.waifupics.view.main.ui.image_info.color_palette.success.ColorPalette
import com.markettwits.waifupics.view.main.ui.image_info.image_card_info.success.SourceAndResource


@Composable
fun ImageInfoCardEmptyAuthor(
    imageData : ImageSourceUi,
    colorPalette : List<List<Int>>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        Divider(thickness = 0.1.dp, color = DirtyWhite)
        SourceAndResource(imageData)
        Divider(thickness = 0.1.dp, color = DirtyWhite)
        ColorPalette(colorPalette)
    }
}
@Composable
fun ImageInfoCardWitAuthor(
    author: AuthorUi,
    imageData : ImageSourceUi,
    colorPalette : List<List<Int>>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        ArtistInfo(author)
        Divider(thickness = 0.1.dp, color = DirtyWhite)
        SourceAndResource(imageData)
        Divider(thickness = 0.1.dp, color = DirtyWhite)
        ColorPalette(colorPalette)
    }
}

