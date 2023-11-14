package com.markettwits.presentation.detail

interface ImageControllerState {
    fun visible() : Boolean
    class Visible() : ImageControllerState {
        override fun visible() = true
    }

    class InVisible : ImageControllerState {
        override fun visible() = false
    }
}