package com.markettwits.image_action.impl

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

interface ImageFileWrapper {

    suspend fun drawableToUri(drawable: Drawable): Uri

    suspend fun pathToUri(imagePath: String): Uri

    abstract class Abstract(private val context: Context) : ImageFileWrapper {

        override suspend fun drawableToUri(drawable: Drawable): Uri {
            val bitmap = drawable.toBitmap()
            val tempFile = File(context.filesDir, LOCAL_IMAGE_NAME)
            try {
                val tempStream: OutputStream =
                    withContext(Dispatchers.IO) {
                        FileOutputStream(tempFile)
                    }
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, tempStream)
                withContext(Dispatchers.IO) {
                    tempStream.flush()
                    tempStream.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return FileProvider.getUriForFile(
                context,
                LOCAL_FILE_PROVIDER,
                tempFile
            )
        }

        override suspend fun pathToUri(imagePath: String): Uri {
            val imageFile = File(imagePath)
            return FileProvider.getUriForFile(context, LOCAL_FILE_PROVIDER, imageFile)
        }
    }

    class Base(context: Context) : Abstract(context)

    companion object {
        private const val LOCAL_FILE_PROVIDER = "com.markettwits.waifupics.fileprovider"
        private const val LOCAL_IMAGE_NAME = "images/shared_image_temp.png"
    }
}