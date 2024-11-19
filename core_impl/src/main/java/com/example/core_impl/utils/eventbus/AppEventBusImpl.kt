package com.example.core_impl.utils.eventbus

import com.example.core_api.utils.eventbus.AppEventBus
import com.example.core_api.utils.eventbus.model.AppEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class AppEventBusImpl @Inject constructor() : AppEventBus {

    private val _sharedFlow = Channel<AppEvent>()

    override val sharedFlow: Flow<AppEvent> = _sharedFlow.receiveAsFlow()

    override fun sendEvent(event: AppEvent) {
        _sharedFlow.trySend(event)
    }
}
