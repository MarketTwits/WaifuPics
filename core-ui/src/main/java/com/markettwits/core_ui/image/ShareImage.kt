package com.markettwits.core_ui.image

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

interface ShareImage {
    fun useAs(imagePath: String)
    fun edit(imagePath: String)
    fun shareImageLocalUrl(imagePath: String)
    fun shareImageUrl(url: String)
    fun shareImageDrawable(image: Drawable)
    abstract class Abstract(private val context: Context) : ShareImage {
        @Deprecated("dont use")
        protected fun loadImageLocal(imageUrl: String): Bitmap? {
            return try {
                BitmapFactory.decodeFile(imageUrl)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        protected fun drawableToUri(drawable: Drawable): Pair<Uri, File> {
            // Convert the drawable to a Bitmap
            val bitmap = drawable.toBitmap()

            // Create a temporary file in the files directory
            val tempFile = File(context.filesDir, "images/shared_image_temp.png")

            // Save the Bitmap to the temporary file
            try {
                val tempStream: OutputStream = FileOutputStream(tempFile)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, tempStream)
                tempStream.flush()
                tempStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            // Get the content URI using FileProvider
            val imageUri = FileProvider.getUriForFile(
                context,
                LOCAL_FILE_PROVIDER,
                tempFile
            )
            return Pair(imageUri, tempFile)
        }
    }

    class Base(private val context: Context) : Abstract(context) {
        override fun useAs(imagePath: String) {
            val imageFile = File(imagePath)
            val contentUri: Uri =
                FileProvider.getUriForFile(context, LOCAL_FILE_PROVIDER, imageFile)

            val intent = Intent(Intent.ACTION_ATTACH_DATA).apply {
                addCategory(Intent.CATEGORY_DEFAULT)
                setDataAndType(contentUri, TYPE_IMAGE)
                putExtra("mimeType", TYPE_IMAGE)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(
                Intent.createChooser(intent, "useAs").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }

        override fun edit(imagePath: String) {
            val imageFile = File(imagePath)
            val contentUri: Uri =
                FileProvider.getUriForFile(context, LOCAL_FILE_PROVIDER, imageFile)

            val intent = Intent(Intent.ACTION_EDIT).apply {
                addCategory(Intent.CATEGORY_DEFAULT)
                setDataAndType(contentUri, TYPE_IMAGE)
                putExtra("mimeType", TYPE_IMAGE)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(
                Intent.createChooser(intent, "Edit").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }

        override fun shareImageLocalUrl(imagePath: String) {
            val imageFile = File(imagePath)
            val contentUri: Uri =
                FileProvider.getUriForFile(context, LOCAL_FILE_PROVIDER, imageFile)
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = TYPE_IMAGE
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            context.startActivity(
                Intent.createChooser(shareIntent, "Share Image")
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }

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

        @SuppressWarnings("add clean after sharing")
        override fun shareImageDrawable(image: Drawable) {
            // Convert the drawable to a Uri and get the temporary file
            val (imageUri, tempFile) = drawableToUri(image)
            // Create an intent to share the image
            val sendIntent: Intent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_STREAM, imageUri)
                type = TYPE_IMAGE
            }
            // Create a chooser and add flags
            val shareIntent = Intent.createChooser(sendIntent, INTENT_NAME)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            context.startActivity(shareIntent)

        }
    }


    private companion object {
        const val LOCAL_FILE_PROVIDER = "com.markettwits.waifupics.fileprovider"
        const val INTENT_NAME = "nekos_api_share_image url"
        const val TYPE_TEXT = "text/plain"
        const val TYPE_IMAGE = "image/*"
    }
}