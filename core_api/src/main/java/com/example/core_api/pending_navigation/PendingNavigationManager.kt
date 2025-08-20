package com.example.core_api.pending_navigation

import com.example.core_api.pending_navigation.model.PendingNavigationState
import kotlinx.coroutines.flow.Flow

interface PendingNavigationManager {

    val pendingNavigationState: Flow<PendingNavigationState>

    fun saveState(state: PendingNavigationState)
}
