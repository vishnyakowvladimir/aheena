package com.example.core.utils.eventbus

import com.example.core.utils.eventbus.model.AppEvent
import kotlinx.coroutines.flow.SharedFlow

interface AppEventBus {

    val sharedFlow: SharedFlow<AppEvent>
    fun sendEvent(event: AppEvent)
}
