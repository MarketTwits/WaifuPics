package com.markettwits.presentation

import android.os.Parcelable
import com.markettwits.navigation.R
import com.markettwits.navigation.Screen
import kotlinx.parcelize.Parcelize

interface NavigationItem : Parcelable {
    val isSelected: Boolean
    val screen: Screen
    val title: Int
    val iconRes : Int
    fun copy(selected : Boolean) : NavigationItem
    @Parcelize
    data class Home(
        override val isSelected: Boolean = true,
        override val screen: Screen = Screen.Main,
        override val title: Int = com.markettwits.core_ui.R.string.main,
        override val iconRes: Int = R.drawable.home_menu_icon
    ) : NavigationItem {
        override fun copy(selected : Boolean) = copy(isSelected = selected)
    }
    @Parcelize
    data class Favorite(
        override val isSelected: Boolean = false,
        override val screen: Screen = Screen.Gallery,
        override val title: Int = com.markettwits.core_ui.R.string.gallery

    ) : NavigationItem {
        override val iconRes: Int = R.drawable.gallery_menu_icon

        override fun copy(selected : Boolean) = copy(isSelected = selected)
    }
    @Parcelize
    data class About(
        override val isSelected: Boolean = false,
        override val screen: Screen = Screen.About,
        override val title: Int = com.markettwits.core_ui.R.string.about,
        override val iconRes: Int = R.drawable.about_menu_icon
    ) : NavigationItem {
        override fun copy(selected : Boolean) = copy(isSelected = selected)
    }
}