package com.example.core_api.utils.eventbus

import com.example.core_api.utils.eventbus.model.AppEvent
import kotlinx.coroutines.flow.Flow

interface AppEventBus {

    val sharedFlow: Flow<AppEvent>
    fun sendEvent(event: AppEvent)
}
