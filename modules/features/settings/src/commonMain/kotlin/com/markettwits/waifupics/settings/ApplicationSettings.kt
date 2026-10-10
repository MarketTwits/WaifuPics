package com.markettwits.waifupics.settings

import com.markettwits.waifupics.settings.params.ColorTheme
import kotlinx.serialization.Serializable


@Serializable
data class ApplicationSettings(
    val theme: ColorTheme,
    val galleryColumns: Int = 0,
    val galleryGroupByMonth: Boolean = false,
    val galleryKeepAspectRatio: Boolean = false
) {

    companion object{
        val defaultApplicationSettings = ApplicationSettings(ColorTheme.System)
    }
}
