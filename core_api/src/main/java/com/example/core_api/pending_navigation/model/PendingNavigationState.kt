package com.example.core_api.pending_navigation.model

sealed interface PendingNavigationState {
    data object None : PendingNavigationState
    data class Action(val action: () -> Unit) : PendingNavigationState
}
