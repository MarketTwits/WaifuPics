package com.markettwits.waifupics.random.components.image_info.image_card_info.success

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.components.text.ImageParametersText
import com.markettwits.core_ui.components.text.SingleLinkText
import com.markettwits.waifupics.random.model.ImageSourceUi

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