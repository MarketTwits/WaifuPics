package com.markettwits.presentation.detail

import com.markettwits.core.communication.StateCommunication
import com.markettwits.core.wrappers.DispatchersList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Thread.sleep

interface ImageControllerPanel {
    fun changeState()
    suspend fun init()
    fun state(): ImageControllerPanelCommunication
    class Base(
        private val dispatcherList: DispatchersList,
        private val communication: ImageControllerPanelCommunication,
    ) : ImageControllerPanel {
        private val scope = CoroutineScope(dispatcherList.main())
        override fun changeState() {
            if (communication.state().value.visible()) communication.map(ImageControllerState.InVisible()) else communication.map(
                ImageControllerState.Visible()
            )
        }

        override suspend fun init() {
            communication.map(ImageControllerState.Visible())
            scope.launch {
                /**
                 * Warning due to implicit usage Dispatcher.IO
                 */
                withContext(dispatcherList.io()) {
                    sleep(INITIAL_SLEEP_DURATION)
                }
                communication.map(ImageControllerState.InVisible())
            }
        }

        override fun state() = communication
    }

    private companion object {
        const val INITIAL_SLEEP_DURATION = 3000L
    }
}

interface ImageControllerPanelCommunication : StateCommunication.Mutable<ImageControllerState> {
    class Base : StateCommunication.Abstract<ImageControllerState>(ImageControllerState.Visible()),
        ImageControllerPanelCommunication
}