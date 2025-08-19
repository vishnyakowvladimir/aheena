package com.example.core_impl.pending_navigation

import com.example.core_api.pending_navigation.PendingNavigationManager
import com.example.core_api.pending_navigation.model.PendingNavigationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class PendingNavigationManagerImpl @Inject constructor(): PendingNavigationManager {

    private val _actionState = MutableStateFlow<PendingNavigationState>(PendingNavigationState.None)
    override val actionState = _actionState.asStateFlow()

    override fun saveState(state: PendingNavigationState) {
        _actionState.update { state }
    }
}
