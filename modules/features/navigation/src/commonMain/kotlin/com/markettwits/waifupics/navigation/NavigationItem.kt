package com.markettwits.waifupics.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.painterResource
import waifupics.modules.features.navigation.generated.resources.Res
import waifupics.modules.features.navigation.generated.resources.about_menu_icon
import waifupics.modules.features.navigation.generated.resources.gallery_menu_icon
import waifupics.modules.features.navigation.generated.resources.home_menu_icon


sealed interface NavigationItem {

    val isSelected: Boolean
    val title: String

    fun copy(selected: Boolean): NavigationItem

    @Composable
    fun icon(): Painter


    data class Home(
        override val isSelected: Boolean = true,
        override val title: String = "Home",
    ) : NavigationItem {

        override fun copy(selected: Boolean) = copy(isSelected = selected)

        @Composable
        override fun icon(): Painter = painterResource(Res.drawable.home_menu_icon)
    }

    data class Favorite(
        override val isSelected: Boolean = false,
        override val title: String = "Favorites"
    ) : NavigationItem {

        override fun copy(selected: Boolean) = copy(isSelected = selected)

        @Composable
        override fun icon(): Painter = painterResource(Res.drawable.gallery_menu_icon)
    }

    data class About(
        override val isSelected: Boolean = false,
        override val title: String = "About",
    ) : NavigationItem {
        override fun copy(selected: Boolean) = copy(isSelected = selected)

        @Composable
        override fun icon(): Painter = painterResource(Res.drawable.about_menu_icon)
    }
}