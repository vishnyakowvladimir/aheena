package com.example.core_api.utils.eventbus.model

sealed interface AppEvent {

    data object OnNoInternetConnection : AppEvent
}
