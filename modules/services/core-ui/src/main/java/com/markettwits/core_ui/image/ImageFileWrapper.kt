package com.markettwits.core_ui.image

//interface ImageFileWrapper {
//    fun drawableToUri(drawable: Drawable): Uri
//    fun pathToUri(imagePath: String): Uri
//    class Base(private val context: Context) : ImageFileWrapper {
//        override fun drawableToUri(drawable: Drawable): Uri {
//            val bitmap = drawable.toBitmap()
//            val tempFile = File(context.filesDir, LOCAL_IMAGE_NAME)
//            try {
//                val tempStream: OutputStream = FileOutputStream(tempFile)
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, tempStream)
//                tempStream.flush()
//                tempStream.close()
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//            return FileProvider.getUriForFile(
//                context,
//                LOCAL_FILE_PROVIDER,
//                tempFile
//            )
//        }
//
//        override fun pathToUri(imagePath: String): Uri {
//            val imageFile = File(imagePath)
//            return FileProvider.getUriForFile(context, LOCAL_FILE_PROVIDER, imageFile)
//        }
//    }
//    companion object {
//        const val LOCAL_FILE_PROVIDER = "com.markettwits.waifupics.fileprovider"
//        const val LOCAL_IMAGE_NAME = "images/shared_image_temp.png"
//    }
//}