package com.markettwits.waifupics.settings.params

import kotlinx.serialization.Serializable

@Serializable
sealed interface ColorTheme {

    @Serializable
    data object DarkTheme : ColorTheme

    @Serializable
    data object LightTheme : ColorTheme

    @Serializable
    data object System : ColorTheme

    @Serializable
    data object Empty : ColorTheme
}