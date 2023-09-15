package com.markettwits.waifupics.view.main.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.markettwits.waifupics.view.main.ui.bottom_pannel.BottomPanel
import com.markettwits.waifupics.view.main.ui.image.loading.ImageLoading
import com.markettwits.waifupics.view.main.ui.image.suceess.ImageCard
import com.markettwits.waifupics.view.main.ui.image_info.image_card_info.loading.ImageCardInfoLoading
import com.markettwits.waifupics.view.main.ui.image_info.image_card_info.success.ImageInfoCard


@Composable
fun MainScreen(
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
       LoadingState()
    }
}
@Composable
fun LoadingState(){
    ImageLoading()
    ImageCardInfoLoading()
    BottomPanel()
}
@Composable
fun SuccessState(){
    ImageCard()
    ImageInfoCard()
    BottomPanel()
}


