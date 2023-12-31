package com.markettwits.waifupics.view.main.ui.image_info.image_card_info.success

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.components.ImageParametersText
import com.markettwits.core_ui.components.MultyLinksText
import com.markettwits.core_ui.components.SingleLinkText
import com.markettwits.core_ui.theme.WaifuPicsTheme
import com.markettwits.random_image.presentation.screen.ImageSourceUi

@Composable
fun SourceAndResource(source : ImageSourceUi) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column {
            SingleLinkText(
                title = "Image source: ",
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
        MultyLinksText(
            title = "Image source",
            links = listOf("https://api.nekosapi.com/v3/images/random?rating=safe&limit=1"),
        )
    }
}
