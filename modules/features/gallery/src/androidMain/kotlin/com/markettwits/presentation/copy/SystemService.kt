package com.markettwits.presentation.copy

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

interface SystemService {

    fun copy(text : String)

    class Base(private val context: Context) : SystemService {

        private val clipBoardService = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        override fun copy(text : String) {
            val clipText = ClipData.newPlainText(COPY_TYPE_TEXT,text)
            clipBoardService.setPrimaryClip(clipText)
        }
    }
    private companion object{
        const val COPY_TYPE_TEXT = "text"
    }
}