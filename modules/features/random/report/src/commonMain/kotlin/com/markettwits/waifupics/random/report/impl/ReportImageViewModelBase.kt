package com.markettwits.waifupics.random.report.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markettwits.waifupics.random.cloud.NekoService
import com.markettwits.waifupics.random.report.api.ReportImageState
import com.markettwits.waifupics.random.report.api.ReportImageViewModel
import com.markettwits.waifupics.result.HandleNetworkResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ReportImageViewModelBase(
    private val nekoService: NekoService,
    private val resultHandler: HandleNetworkResult,
    private val mapper: ReportedImageMapper
) : ReportImageViewModel, ViewModel() {

    override val state: MutableStateFlow<ReportImageState> =
        MutableStateFlow(ReportImageState.Base)

    override val labels: MutableStateFlow<ReportImageViewModel.Labels> =
        MutableStateFlow(ReportImageViewModel.Labels.Empty)

    override fun onClickCancel() {
        viewModelScope.launch {
            labels.emit(ReportImageViewModel.Labels.GoBack)
        }
    }

    override fun onClickAllow(imageId: Int) {
        viewModelScope.launch {
            state.emit(ReportImageState.Loading)
            val result = resultHandler.tryRequest {
                nekoService.report(imageId)
            }.map(mapper)
            state.emit(result)
            if (result is ReportImageState.Success){
                delay(2000L)
                labels.emit(ReportImageViewModel.Labels.GoBack)
            }

//            runCatching {
//                nekoService.report(imageId)
//            }.onFailure {
//                print(it)
//            }
            // state.emit(result.map(mapper))
        }
    }

    override fun resetLabels() {
        viewModelScope.launch {
            labels.emit(ReportImageViewModel.Labels.Empty)
        }
    }
}