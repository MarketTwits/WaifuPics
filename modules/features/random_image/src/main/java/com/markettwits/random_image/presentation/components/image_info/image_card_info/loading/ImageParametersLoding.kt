package com.markettwits.waifupics.view.main.ui.image_info.image_card_info.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.components.progress.LoadingBox

@Preview
@Composable
fun ImageParametersLoading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column( modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
        ) {
            Row {
                LoadingBox(modifier = Modifier
                    .padding(4.dp)
                    .width(80.dp))
                LoadingBox(modifier = Modifier
                    .padding(4.dp)
                    .width(90.dp))
            }
            Row {
                LoadingBox(modifier = Modifier
                    .padding(4.dp)
                    .width(110.dp))
                LoadingBox(modifier = Modifier
                    .padding(4.dp)
                    .width(55.dp))
            }
        }
    }
}