package com.example.core_impl.pending_navigation

import com.example.core_api.pending_navigation.PendingNavigationManager
import com.example.core_api.pending_navigation.model.PendingNavigationState
import com.example.core_api.utils.coroutines.OneTimeSharedFlow
import javax.inject.Inject

class PendingNavigationManagerImpl @Inject constructor() : PendingNavigationManager {

    private val _pendingNavigationState = OneTimeSharedFlow<PendingNavigationState>()
    override val pendingNavigationState = _pendingNavigationState.flow

    override fun saveState(state: PendingNavigationState) {
        _pendingNavigationState.emit(state)
    }
}
