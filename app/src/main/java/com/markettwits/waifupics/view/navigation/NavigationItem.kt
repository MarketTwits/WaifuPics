package com.markettwits.waifupics.view.navigation

import android.os.Parcelable
import com.markettwits.waifupics.R
import com.markettwits.waifupics.view.navigation.model.Screen
import kotlinx.parcelize.Parcelize

interface NavigationItem : Parcelable {
    val isSelected: Boolean
    val screen: Screen
    val title: Int
    fun copy(selected : Boolean) : NavigationItem
    @Parcelize
    data class Home(
        override val isSelected: Boolean = true,
        override val screen: Screen = Screen.Main,
        override val title: Int = R.string.main
    ) : NavigationItem {
        override fun copy(selected : Boolean) = copy(isSelected = selected)
    }
    @Parcelize
    data class Favorite(
        override val isSelected: Boolean = false,
        override val screen: Screen = Screen.Gallery,
        override val title: Int = R.string.gallery
    ) : NavigationItem {
        override fun copy(selected : Boolean) = copy(isSelected = selected)
    }
    @Parcelize
    data class About(
        override val isSelected: Boolean = false,
        override val screen: Screen = Screen.About,
        override val title: Int = R.string.about
    ) : NavigationItem {
        override fun copy(selected : Boolean) = copy(isSelected = selected)
    }
}