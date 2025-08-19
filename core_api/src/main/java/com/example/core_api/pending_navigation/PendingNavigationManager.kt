package com.example.core_api.pending_navigation

import com.example.core_api.pending_navigation.model.PendingNavigationState
import kotlinx.coroutines.flow.StateFlow

interface PendingNavigationManager {

    val actionState: StateFlow<PendingNavigationState>

    fun saveState(state: PendingNavigationState)
}
