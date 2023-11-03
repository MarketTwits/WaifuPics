package com.markettwits.random_image.ui.bottom_pannel

interface BottomPanelUiState{
    fun baseBottomEnabled() : Boolean
    fun refresh() : Boolean
    abstract class Abstract() : BottomPanelUiState {
        override fun baseBottomEnabled() = true
        override fun refresh() = false
    }
    object Loading : Abstract() {
        override fun baseBottomEnabled() = false

        override fun refresh() = true
    }
    object Error : Abstract() {
        override fun baseBottomEnabled() = false
    }
    object Success : Abstract()
}