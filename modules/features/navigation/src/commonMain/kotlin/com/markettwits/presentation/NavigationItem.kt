package com.markettwits.presentation


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.markettwits.navigation.Screen
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import waifupics.modules.features.navigation.generated.resources.Res
import waifupics.modules.features.navigation.generated.resources.about_menu_icon
import waifupics.modules.features.navigation.generated.resources.gallery_menu_icon
import waifupics.modules.features.navigation.generated.resources.home_menu_icon

@Serializable
sealed interface NavigationItem {

    val isSelected: Boolean
    val screen: Screen
    val title: String

    fun copy(selected: Boolean): NavigationItem

    @Composable
    fun icon() : Painter

    @Serializable
    data class Home(
        override val isSelected: Boolean = true,
        override val screen: Screen = Screen.Main,
        override val title: String = "Home",
    ) : NavigationItem {

        override fun copy(selected: Boolean) = copy(isSelected = selected)

        @Composable
        override fun icon(): Painter =  painterResource(Res.drawable.home_menu_icon)
    }

    @Serializable
    data class Favorite(
        override val isSelected: Boolean = false,
        override val screen: Screen = Screen.Gallery,
        override val title: String = "Favorites"
    ) : NavigationItem {

        override fun copy(selected: Boolean) = copy(isSelected = selected)

        @Composable
        override fun icon(): Painter =  painterResource(Res.drawable.gallery_menu_icon)
    }

    @Serializable
    data class About(
        override val isSelected: Boolean = false,
        override val screen: Screen = Screen.About,
        override val title: String = "About",
    ) : NavigationItem {
        override fun copy(selected: Boolean) = copy(isSelected = selected)

        @Composable
        override fun icon(): Painter = painterResource(Res.drawable.about_menu_icon)
    }


}