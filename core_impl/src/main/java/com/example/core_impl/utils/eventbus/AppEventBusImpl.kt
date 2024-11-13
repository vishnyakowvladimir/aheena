package com.example.core_impl.utils.eventbus

import com.example.core_api.utils.eventbus.AppEventBus
import com.example.core_api.utils.eventbus.model.AppEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class AppEventBusImpl @Inject constructor() : AppEventBus {

    private val _sharedFlow = MutableSharedFlow<AppEvent>(
        replay = Int.MAX_VALUE,
    )
    
    override val sharedFlow: SharedFlow<AppEvent> = _sharedFlow.asSharedFlow()

    override fun sendEvent(event: AppEvent) {
        _sharedFlow.tryEmit(event)
    }
}
