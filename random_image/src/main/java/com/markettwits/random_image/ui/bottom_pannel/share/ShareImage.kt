package com.markettwits.random_image.ui.bottom_pannel.share

import android.content.Context
import android.content.Intent

interface ShareImage {
    fun shareImageUrl(url: String)
    class Base(private val context: Context) : ShareImage {
        override fun shareImageUrl(url: String) {

            val sendIntent: Intent = Intent(
                Intent.ACTION_SEND
            ).apply {
                putExtra(Intent.EXTRA_TEXT, url)
                type = TYPE_TEXT
            }
            val shareIntent = Intent.createChooser(
                sendIntent, INTENT_NAME
            )
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(shareIntent)
        }
    }

    private companion object {
        const val INTENT_NAME = "nekos_api_share_image url"
        const val TYPE_TEXT = "text/plain"
    }
}