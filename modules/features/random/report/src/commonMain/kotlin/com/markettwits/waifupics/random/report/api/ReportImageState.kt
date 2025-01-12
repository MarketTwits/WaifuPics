package com.markettwits.waifupics.random.report.api


sealed interface ReportImageState {

    data object Loading : ReportImageState

    data object Base : ReportImageState

    data class Failed(val message: String) : ReportImageState

    data class Success(val message: String) : ReportImageState
}