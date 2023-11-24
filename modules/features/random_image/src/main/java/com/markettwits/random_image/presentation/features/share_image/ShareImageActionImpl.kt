package com.markettwits.random_image.presentation.features.share_image

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.core.app.ShareCompat
import com.markettwits.core_ui.image.ImageFileWrapper
import com.markettwits.core_ui.image.ImageIntentAction


interface ShareImageAction : ImageIntentAction.ShareImage {
    fun shareImageDrawable(image: Drawable)
    class Base(private val context: Context,
               private val imageFileWrapper: ImageFileWrapper
    ) :
        ShareImageAction {
        override fun shareImageDrawable(image: Drawable) {
            val imageUri = imageFileWrapper.drawableToUri(image)
            val intent = ShareCompat
                .IntentBuilder(context)
                .setType(TYPE_IMAGE)
                .addStream(imageUri)
                .intent
            context.startActivity(
                Intent.createChooser(intent, INTENT_ACTION_SHARE_IMAGE).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }

        override suspend fun shareImage(imagePath: String) {
            val intent = ShareCompat
                .IntentBuilder(context)
                .setType(TYPE_IMAGE)
                .addStream(imageFileWrapper.pathToUri(imagePath))
                .intent
            context.startActivity(
                Intent.createChooser(intent, INTENT_ACTION_SHARE_IMAGE).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }
    private companion object{

        const val INTENT_ACTION_SHARE_IMAGE = "Share Image"
        const val TYPE_IMAGE = "image/*"
    }

}