package com.markettwits.waifupics.gallery.presentation.screens.list.allert_dialog

interface ProtectedUiState {
    fun state() : ProtectedUiState
    abstract class Abstract : ProtectedUiState {
        override fun state() = this
    }
    object Hide : Abstract()
    object Single : Abstract()
    object Show: Abstract()

}
interface ProtectedUiStateEvent{
    fun state() : ProtectedUiStateEvent
    abstract class Abstract() : ProtectedUiStateEvent {
        override fun state() = this
    }
    object HideProtected : Abstract()
    object ShowDialog : Abstract()
    object Show : Abstract()
}