package com.markettwits.waifupics.gallery.presentation.copy

interface SystemService {

    fun copy(text : String)

    private companion object{
        const val COPY_TYPE_TEXT = "text"
    }
}