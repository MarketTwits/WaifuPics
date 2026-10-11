package com.markettwits.waifupics.root.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.remember
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import com.markettwits.theme.components.WaifuSpacing
import com.markettwits.waifupics.gallery.common.GalleryUndoSnackbarHost
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.statusBars
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.markettwits.waifupics.about.AboutScreen
import com.markettwits.waifupics.gallery.item.components.ImageScreenFull
import com.markettwits.waifupics.gallery.items.components.GalleryScreen
import com.markettwits.waifupics.gallery.items.components.animations.GalleryImageTransition
import com.markettwits.waifupics.navigation.components.NavigationScreen
import com.markettwits.waifupics.navigation.model.NavigationItem
import com.markettwits.waifupics.random.components.RandomImageScreen


@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
fun MainScreen(
    modifier: Modifier = Modifier,
    onImageViewerChanged: (Boolean) -> Unit = {},
) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var galleryReturnId by rememberSaveable { mutableStateOf<Int?>(null) }
    var galleryOverlayHeight by remember { mutableStateOf(0.dp) }
    var viewerOverlayHeight by remember { mutableStateOf(0.dp) }

    val imageViewer = navBackStackEntry?.destination?.route == Screen.GalleryItem.route()
    LaunchedEffect(imageViewer) { onImageViewerChanged(imageViewer) }

    NavigationScreen(
        selectedTitle = when (navBackStackEntry?.destination?.route) {
            Screen.About.route() -> "About"
            Screen.Gallery.route(), Screen.GalleryItem.route() -> "Favorites"
            else -> "Home"
        },
        isShowTopBar = navBackStackEntry?.destination?.route != Screen.GalleryItem.route(),
        overlayTopBar = navBackStackEntry?.destination?.route.let { it == null || it == Screen.Main.route() },
        onClickNavigationItem = {
            galleryReturnId = null
            navController.navigate(it.mapToScreen().route())
        }, content = { paddingValues ->
            // The outgoing grid must not move when the viewer hides the navigation toolbar.
            val galleryPadding = PaddingValues(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 56.dp)
            val overlayHeight = when (navBackStackEntry?.destination?.route) {
                Screen.GalleryItem.route() -> viewerOverlayHeight
                Screen.Gallery.route() -> galleryOverlayHeight
                else -> 0.dp
            }
            val snackbarBottom by animateDpAsState(maxOf(overlayHeight,
                WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()) + WaifuSpacing.content,
                tween(180), label = "Undo panel bottom inset")
            Box(Modifier.fillMaxSize()) {
                SharedTransitionLayout {
                    val sharedScope = this
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Main.route(),
                        modifier = modifier
                    ) {
                        composable(Screen.Main.route(), exitTransition = {
                            if (targetState.destination.route == Screen.Gallery.route()) ExitTransition.None else null
                        }) {
                            RandomImageScreen(paddingValues = paddingValues)
                        }
                        composable(Screen.About.route(), exitTransition = {
                            if (targetState.destination.route == Screen.Gallery.route()) ExitTransition.None else null
                        }) {
                            AboutScreen(paddingValues = paddingValues)
                        }
                        composable(Screen.Gallery.route(),
                            enterTransition = { EnterTransition.None }, exitTransition = { fadeOut(tween(220)) },
                            popEnterTransition = {
                                if (initialState.destination.route == Screen.GalleryItem.route()) fadeIn(tween(220))
                                else EnterTransition.None
                            }) {
                            GalleryScreen(
                                onClickGoDetail = {
                                    galleryReturnId = null
                                    navController.navigate(Screen.GalleryItem.route()) { launchSingleTop = true }
                                }, paddingValues = galleryPadding,
                                imageTransition = GalleryImageTransition(sharedScope, this),
                                returnToImageId = galleryReturnId,
                                onBottomOverlayHeightChanged = { galleryOverlayHeight = it }
                            )
                        }
                        composable(Screen.GalleryItem.route(),
                            enterTransition = { fadeIn(tween(240)) }, exitTransition = { fadeOut(tween(240)) },
                            popExitTransition = { fadeOut(tween(240)) }) {
                            ImageScreenFull(imageTransition = GalleryImageTransition(sharedScope, this),
                                onBottomOverlayHeightChanged = { viewerOverlayHeight = it },
                                onCurrentImageChanged = { galleryReturnId = it }) {
                                navController.popBackStack()
                            }
                        }
                    }
                }
                GalleryUndoSnackbarHost(Modifier.align(Alignment.BottomCenter)
                    .padding(horizontal = WaifuSpacing.content)
                    .padding(bottom = snackbarBottom))
            }
        })
}

private fun NavigationItem.mapToScreen(): Screen =
    when (this) {
        is NavigationItem.About -> Screen.About
        is NavigationItem.Favorite -> Screen.Gallery
        is NavigationItem.Home -> Screen.Main
    }
