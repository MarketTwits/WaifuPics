package com.markettwits.image_action.api


import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.asImage
import coil3.request.ImageRequest
import coil3.toBitmap
import org.jetbrains.skiko.toBufferedImage
import java.awt.FileDialog
import java.awt.Frame
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO


class ImageLoaderJvm(private val platformContext: PlatformContext) : ImageLoader {

    @OptIn(ExperimentalCoilApi::class)
    override suspend fun saveToGallery(imageUrl: String) {
        val imageLoader = coil3.ImageLoader(platformContext)
        val request = ImageRequest.Builder(platformContext)
            .data(imageUrl)
            .build()
        val disposable = imageLoader.enqueue(request)
        disposable.job.await().also { result ->
            val bitmap = result.image?.toBitmap()?.asImage()?.bitmap?.toBufferedImage()
                ?: throw IllegalArgumentException("Can't convert bitmap")
            saveBitmapDialog(bitmap)
        }
    }

    private fun saveBitmapDialog(bitmap: BufferedImage) {
        val frame = Frame()
        val fileDialog = FileDialog(frame, "Save Image", FileDialog.SAVE)
        fileDialog.isVisible = true

        val directory = fileDialog.directory
        val fileName = fileDialog.file

        if (directory != null && fileName != null) {
            val outputFile = File(directory, "$fileName.png")
            try {
                ImageIO.write(bitmap, "png", outputFile)
                println("Image save in  $outputFile")
            } catch (e: IOException) {
                println("Fail: ${e.message}")
            }
        }
    }

}