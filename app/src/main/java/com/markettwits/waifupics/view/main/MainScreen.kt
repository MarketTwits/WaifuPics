package com.markettwits.waifupics.view.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.markettwits.waifupics.view.main.bottom_pannel.BottomPanel
import com.markettwits.waifupics.view.main.image.loading.ImageLoading
import com.markettwits.waifupics.view.main.image.suceess.ImageCard
import com.markettwits.waifupics.view.main.image_info.image_card_info.loading.ImageCardInfoLoading
import com.markettwits.waifupics.view.main.image_info.image_card_info.success.ImageInfoCard


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
        SuccessState()
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


