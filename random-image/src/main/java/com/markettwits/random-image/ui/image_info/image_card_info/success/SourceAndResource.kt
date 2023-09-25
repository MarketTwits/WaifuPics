package com.markettwits.waifupics.view.main.ui.image_info.image_card_info.success

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.base.ImageParametersText
import com.markettwits.`random-image`.ui.ImageSourceUi
import com.markettwits.waifupics.theame.theme.WaifuPicsTheme

@Composable
fun SourceAndResource(source : ImageSourceUi) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column {
            ImageParametersText(
                title = "Image source",
                content = source.source,
                link = source.sourceUrl,
            )
            ImageParametersText(
                title = "Age rating",
                content = source.ageRating
            )
        }
    }
}
@Preview
@Composable
private fun SourceAndResourcePreview(){
    WaifuPicsTheme {
        //SourceAndResource()
    }
}
