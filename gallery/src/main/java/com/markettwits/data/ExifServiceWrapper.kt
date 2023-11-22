package com.markettwits.data

import android.content.Context
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


interface ExifServiceWrapper {
    fun imagePath(path: String)
    class Base(private val context: Context) : ExifServiceWrapper {
        override fun imagePath(path: String) {
            val file = File(path)

            if (file.exists()) {
                // Get file details
                val fileName = file.name
                val filePath = file.absolutePath
                val fileSize = file.length() // in bytes

                // Get creation time
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val creationTime = dateFormat.format(Date(file.lastModified()))

                // Decode the image to get its dimensions without loading it into memory
                val options = BitmapFactory.Options()
                options.inJustDecodeBounds = true
                BitmapFactory.decodeFile(path, options)

                // Extract image information
                val imageWidth = options.outWidth
                val imageHeight = options.outHeight
                val imageMimeType = options.outMimeType

                val exifInterface = ExifInterface(path)

                // Get the author information
                val author = exifInterface.getAttribute(ExifInterface.TAG_ARTIST)

                // Log or use the author information as needed
                Log.d("ImageInfo", "Author: $author")
                // Log the information or use it as needed
                Log.d("ImageInfo", "File Name: $fileName")
                Log.d("ImageInfo", "File Path: $filePath")
                Log.d("ImageInfo", "File Size: $fileSize bytes")
                Log.d("ImageInfo", "Creation Time: $creationTime")
                Log.d(
                    "ImageInfo",
                    "Width: $imageWidth, Height: $imageHeight, MIME Type: $imageMimeType"
                )
                Log.d("ImageInfo", "                                           ")
            } else {
                Log.e("ImageInfo", "File not found: $path")
            }
        }

    }

    companion object {
        const val LOCAL_FILE_PROVIDER = "com.markettwits.waifupics.fileprovider"
    }
}




