package com.markettwits.random_image.ui.bottom_pannel.share

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

interface ShareImage {
    fun shareImageUrl(url: String)
    fun shareImageDrawable(image: Drawable)
    abstract class Abstract(private val context: Context) : ShareImage {
        protected fun drawableToUri(drawable: Drawable): Uri {

            //val bitmap = drawable.toBitmap().compress(Bitmap.CompressFormat.PNG, 100, ByteArrayOutputStream())
            val path = MediaStore.Images.Media.insertImage(
                context.contentResolver,
                drawable.toBitmap(),
                "Title",
                null
            )
            return Uri.parse(path)
        }
    }

    class Base(private val context: Context) : Abstract(context) {
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

        override fun shareImageDrawable(image: Drawable) {

            val sendIntent: Intent = Intent(
                Intent.ACTION_SEND
            ).apply {
                putExtra(Intent.EXTRA_STREAM, drawableToUri(image))
                type = TYPE_IMAGE
            }
            val shareIntent = Intent.createChooser(
                sendIntent, INTENT_NAME
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            context.startActivity(shareIntent)
        }
    }

    private companion object {
        const val INTENT_NAME = "nekos_api_share_image url"
        const val TYPE_TEXT = "text/plain"
        const val TYPE_IMAGE = "image/*"
    }
}