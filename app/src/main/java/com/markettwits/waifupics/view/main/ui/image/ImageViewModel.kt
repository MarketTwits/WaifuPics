package com.markettwits.waifupics.view.main.ui.image

import android.graphics.drawable.Drawable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.markettwits.waifupics.core.communication.Communication
import com.markettwits.waifupics.view.main.data.NekoService

class ImageViewModel(
    private val communication: ImageCommunication,
    private val service: NekoService
) : ViewModel(),
    Communication.Observe<Drawable> {
    override fun observe(owner: LifecycleOwner, observer: Observer<Drawable>) {
        communication.observe(owner, observer)
    }
    fun map(image : Drawable){
        communication.map(image)
    }
    fun fetch() = communication.fetch()
}

interface ImageCommunication : Communication.Mutable<Drawable> {
    class Base : Communication.Abstract<Drawable>(), ImageCommunication
}



