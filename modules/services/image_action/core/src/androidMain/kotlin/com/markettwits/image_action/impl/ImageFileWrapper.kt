package com.markettwits.image_action.impl

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.request.ImageRequest
import coil3.request.allowHardware
import coil3.toBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


interface ImageFileWrapper {

    suspend fun drawableToUri(drawable: Drawable): Uri

    suspend fun pathToUri(imagePath: String): Uri

    suspend fun imageUrlToUri(imageUrl : String, uri : (Uri) -> Unit)

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

        override suspend fun pathToUri(imagePath: String): Uri  =
            FileProvider.getUriForFile(context, LOCAL_FILE_PROVIDER, File(imagePath))

        @OptIn(ExperimentalCoilApi::class)
        override suspend fun imageUrlToUri(imageUrl : String,uri : (Uri) -> Unit) {
            val imageLoader = ImageLoader(context)
            val request = ImageRequest.Builder(context)
                .data(imageUrl)
                .allowHardware(false)
                .build()            val disposable = imageLoader.enqueue(request)
            disposable.job.await().also { result ->
                val bitmap = result.image?.toBitmap() // your imageView here.
                val tempFile = File(context.filesDir, LOCAL_IMAGE_NAME)
                try {
                    val tempStream: OutputStream =
                        withContext(Dispatchers.IO) {
                            FileOutputStream(tempFile)
                        }
                    bitmap?.compress(Bitmap.CompressFormat.PNG, 100, tempStream)
                    withContext(Dispatchers.IO) {
                        tempStream.flush()
                        tempStream.close()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                val urils =  FileProvider.getUriForFile(
                    context,
                    LOCAL_FILE_PROVIDER,
                    tempFile
                )
                uri(urils)
//                val bytes = ByteArrayOutputStream()
//                bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
//                val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "tempimage", null)
//                val uril = Uri.parse(path)
//                uri(uril)
            }
        }
    }

    class Base(context: Context) : Abstract(context)

    companion object {
        private const val LOCAL_FILE_PROVIDER = "com.markettwits.waifupics.fileprovider"
        private const val LOCAL_IMAGE_NAME = "images/shared_image_temp.png"
    }
}