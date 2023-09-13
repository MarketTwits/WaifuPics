package com.markettwits.waifupics.view.main.image_info.image_card_info.success

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.markettwits.waifupics.ui.theme.WaifuPicsTheme
import com.markettwits.waifupics.view.base.ImageParametersText

@Composable
fun SourceAndResource() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column {
            ImageParametersText(
                title = "Image source: ",
                content = "Pixv",
                link = "www.google.com"
            )
            ImageParametersText(
                title = "Age rating: ",
                content = "Questionable",
                link = "https://www.youtube.com/"
            )
        }
    }
}
@Preview
@Composable
private fun SourceAndResourcePreview(){
    WaifuPicsTheme {
        SourceAndResource()
    }
}
