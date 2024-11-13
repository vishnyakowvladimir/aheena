package com.example.core_api.utils.eventbus

import com.example.core_api.utils.eventbus.model.AppEvent
import kotlinx.coroutines.flow.SharedFlow

interface AppEventBus {

    val sharedFlow: SharedFlow<AppEvent>
    fun sendEvent(event: AppEvent)
}
