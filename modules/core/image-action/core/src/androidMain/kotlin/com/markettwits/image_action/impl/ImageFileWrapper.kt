package com.markettwits.image_action.impl

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.request.ImageRequest
import coil3.request.allowHardware
import coil3.toBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.UUID


interface ImageFileWrapper {

    suspend fun drawableToUri(drawable: Drawable): Uri

    suspend fun pathToUri(imagePath: String): Uri

    suspend fun imageUrlToUri(imageUrl: String, uri: (Uri) -> Unit)

    fun deleteFileByUri(uri: Uri)

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

        override suspend fun pathToUri(imagePath: String): Uri =
            FileProvider.getUriForFile(context, LOCAL_FILE_PROVIDER, File(imagePath))

        @OptIn(ExperimentalCoilApi::class)
        override suspend fun imageUrlToUri(imageUrl: String, uri: (Uri) -> (Unit)) {
            val imageLoader = ImageLoader(context)
            val request = ImageRequest.Builder(context)
                .data(imageUrl)
                .allowHardware(false)
                .build()
            val disposable = imageLoader.enqueue(request)
            disposable.job.await().also { result ->
                uri(
                    bitmapToUri(
                        result.image?.toBitmap()
                            ?: throw IllegalStateException("cant create bitmap")
                    )
                )
            }
        }


        private fun bitmapToUri(bitmap: Bitmap): Uri {
            val file = File(context.cacheDir, "${UUID.randomUUID()}.jpg")
            try {
                val stream: OutputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                stream.flush()
                stream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return FileProvider.getUriForFile(context, LOCAL_FILE_PROVIDER, file)
        }

        @RequiresApi(Build.VERSION_CODES.R)
        override fun deleteFileByUri(uri: Uri) {
            val filePath: String? = uri.path

            if (filePath != null) {
                val fileToDelete = File(filePath)
                if (fileToDelete.exists()) {
                    val deleted = fileToDelete.delete()
                    if (deleted) {
                        Log.e("mt05", "File successfully deleted ")
                    } else {
                        Log.e("mt05", "Can't delete file ")
                    }
                } else {
                    Log.e("mt05", "File does not exist")
                }
            } else {
                Log.e("mt05", "File path does not exist")
            }
        }
    }


    class Base(context: Context) : Abstract(context)

    companion object {
        private const val LOCAL_FILE_PROVIDER = "com.markettwits.waifupics.fileprovider"
        private const val LOCAL_IMAGE_NAME = "images/shared_image_temp.png"
    }
}
