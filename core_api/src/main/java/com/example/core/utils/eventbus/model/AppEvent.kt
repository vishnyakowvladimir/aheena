package com.example.core.utils.eventbus.model

sealed interface AppEvent {

    data object OnNoInternetConnection : AppEvent
}
