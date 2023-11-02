package com.markettwits.presentation

import androidx.lifecycle.ViewModel
import com.markettwits.data.GalleryRepository

class GalleryScreenViewModel(
    private val repository: GalleryRepository,
): ViewModel() {

}
interface GalleryScreenFuture{
    fun delete(imageId : Long)
    fun swipeLeft()
    fun swipeRight()
}