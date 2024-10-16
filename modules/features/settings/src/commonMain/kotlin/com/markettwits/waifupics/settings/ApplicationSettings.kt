package com.markettwits.waifupics.settings

import com.markettwits.waifupics.settings.params.ColorTheme
import kotlinx.serialization.Serializable


@Serializable
data class ApplicationSettings(val theme: ColorTheme){

    companion object{
        val defaultApplicationSettings = ApplicationSettings(ColorTheme.System)
    }
}
