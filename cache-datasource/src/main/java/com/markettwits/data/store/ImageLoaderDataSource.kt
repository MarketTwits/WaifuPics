package com.markettwits.data.store

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Log
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.markettwits.data.ImageRepository
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.UUID

interface ImageLoaderDataSource {
    suspend fun deleteImage(imageUrl: String)
    suspend fun loadImage(imageUrl: String): String
    suspend fun saveToGallery(imageUrl: String)
    class Base(private val context: Context) : ImageLoaderDataSource {
        override suspend fun deleteImage(imageUrl: String) {
            val file = File(imageUrl)
            try {
                file.delete()
            }catch (e : Exception){
                throw RuntimeException("delete error :${e.cause}")
            }
        }

        override suspend fun loadImage(imageUrl: String) : String {
            val imageLoader = ImageLoader.Builder(context).build()
            val request = ImageRequest.Builder(context)
                .data(imageUrl)
                .build()
            val result = imageLoader.execute(request)

            val imageDrawable = (result as SuccessResult).drawable
            val imageBitmap = imageDrawable.toBitmap()
            val a = saveBitmapToStorage(imageBitmap, "test random data: ${UUID.randomUUID()}")
            Log.d("mt05", a.toString())
            return a.imagePath
        }

        override suspend fun saveToGallery(imageUrl: String) {
            val name = "NekosAPI, ${UUID.randomUUID()}.png"
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, name)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.TITLE, name)
                put(MediaStore.Images.Media.DESCRIPTION, name)
                put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/NekosLand")
            }
            val imageUri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            try {
                val inputStream = FileInputStream(imageUrl)
                val outputStream = context.contentResolver.openOutputStream(imageUri!!)
                val buffer = ByteArray(1024)
                var length: Int
                while (inputStream.read(buffer).also { length = it } > 0) {
                    outputStream?.write(buffer, 0, length)
                }
                inputStream.close()
                outputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        fun saveBitmapToStorage(
            bitmap: Bitmap,
            metadata: String
        ): ImageRepository.Base.SavedImageInfo {
            val fileName = "image_${UUID.randomUUID()}.png"
            val directory = File(context.filesDir, "images") // Use your app's folder name
            if (!directory.exists()) {
                directory.mkdirs()
            }

            val file = File(directory, fileName)
            var outputStream: FileOutputStream? = null

            try {
                outputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                val imagePath = file.absolutePath
                return ImageRepository.Base.SavedImageInfo(imagePath, metadata)
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    outputStream?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return ImageRepository.Base.SavedImageInfo("", "")
        }

    }
}