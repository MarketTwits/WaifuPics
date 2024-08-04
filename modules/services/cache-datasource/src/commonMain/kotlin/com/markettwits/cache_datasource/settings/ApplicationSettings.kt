package com.markettwits.cache_datasource.settings

import kotlinx.serialization.Serializable


@Serializable
data class ApplicationSettings( val theme: ColorTheme){

    companion object{
        val defaultApplicationSettings = ApplicationSettings(ColorTheme.System)
    }
}
@Serializable
sealed interface ColorTheme {

    @Serializable
    data object DarkTheme : ColorTheme

    @Serializable
    data object LightTheme : ColorTheme

    @Serializable
    data object System : ColorTheme
}