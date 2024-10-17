package com.markettwits.waifupics.random.report.api

import kotlinx.coroutines.flow.StateFlow

interface ReportImageViewModel {

    val state: StateFlow<ReportImageState>

    val labels: StateFlow<Labels>

    fun onClickCancel()

    fun onClickAllow(imageId: Int)

    fun resetLabels()

    sealed interface Labels {
        data object GoBack : Labels

        data object Empty : Labels
    }

}