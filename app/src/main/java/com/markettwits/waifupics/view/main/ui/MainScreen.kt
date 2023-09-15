package com.markettwits.waifupics.view.main.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.markettwits.waifupics.core.WaifuPicsApp
import com.markettwits.waifupics.view.main.data.net.models.RandomImageCloud
import com.markettwits.waifupics.view.main.ui.bottom_pannel.BottomPanel
import com.markettwits.waifupics.view.main.ui.image.ImageViewModel
import com.markettwits.waifupics.view.main.ui.image.NetworkState
import com.markettwits.waifupics.view.main.ui.image.loading.ImageLoading
import com.markettwits.waifupics.view.main.ui.image.suceess.ImageCard
import com.markettwits.waifupics.view.main.ui.image_info.image_card_info.loading.ImageCardInfoLoading
import com.markettwits.waifupics.view.main.ui.image_info.image_card_info.success.ImageInfoCard


@Composable
fun MainScreen(
    paddingValues: PaddingValues,
) {
    val viewModel = (LocalContext.current.applicationContext as WaifuPicsApp).viewModel(
        checkNotNull(LocalViewModelStoreOwner.current), ImageViewModel::class.java
    )
    val state = viewModel.state.observeAsState()
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state.value) {
            is NetworkState.Loading -> LoadingState()
            is NetworkState.Success -> SuccessState((state.value as NetworkState.Success<RandomImageCloud>).data!!)
            else -> {}
        }
    }
}

@Composable
fun LoadingState() {
    ImageLoading()
    ImageCardInfoLoading()
    BottomPanel()
}

@Composable
fun SuccessState(state : RandomImageCloud) {
    ImageCard(state)
    ImageInfoCard(state)
    BottomPanel()
}


