package com.example.core_api.pending_navigation.model

import com.example.core_api.navigation.base.BaseDestination

sealed interface PendingNavigationState {
    data class Destination(val destination: BaseDestination) : PendingNavigationState
}
