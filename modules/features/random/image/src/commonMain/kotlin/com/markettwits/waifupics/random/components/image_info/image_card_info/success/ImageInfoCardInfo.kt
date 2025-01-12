package com.markettwits.waifupics.random.components.image_info.image_card_info.success

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
import com.markettwits.waifupics.random.components.image_info.color_palette.ColorPalette
import com.markettwits.waifupics.random.model.ImageSourceUi


@Composable
fun ImageInfoCardEmptyAuthor(
    imageData : ImageSourceUi,
    colorPalette : List<List<Int>>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(10.dp)
            .clip(Shapes.medium)
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        HorizontalDivider(thickness = 0.1.dp, color = DirtyWhite)
        SourceAndResource(imageData)
        HorizontalDivider(thickness = 0.1.dp, color = DirtyWhite)
        ColorPalette(colorPalette)
    }
}