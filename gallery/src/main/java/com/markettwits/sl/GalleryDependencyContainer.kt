package com.markettwits.sl

import androidx.lifecycle.ViewModel
import com.markettwits.core.sl.DependencyContainer
import com.markettwits.presentation.GalleryViewModel

class GalleryDependencyContainer(
    private val dependencyContainer: DependencyContainer
) : DependencyContainer {
    override fun module(className: Class<out ViewModel>) = when(className){
        GalleryViewModel::class.java -> GalleryModule()
        else -> dependencyContainer.module(className)
    }
}