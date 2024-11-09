package com.example.core_impl.utils.eventbus

import com.example.core.utils.eventbus.AppEventBus
import com.example.core.utils.eventbus.model.AppEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class AppEventBusImpl @Inject constructor() : AppEventBus {

    private val sharedFlow = MutableSharedFlow<AppEvent>(
        replay = Int.MAX_VALUE,
    )

    override fun subscribe(): SharedFlow<AppEvent> {
        return sharedFlow.asSharedFlow()
    }

    override fun sendEvent(event: AppEvent) {
        sharedFlow.tryEmit(event)
    }


}
