package com.example.feature_authentication.presentation.biometric

import androidx.lifecycle.viewModelScope
import com.example.core.presentation.base.BaseViewModel
import com.example.core.utils.extension.mapData
import com.example.feature_authentication.presentation.biometric.mapper.BiometricsMapper
import com.example.feature_authentication.presentation.biometric.mvi.handler.BiometricsSideEffectHandler
import com.example.feature_authentication.presentation.biometric.mvi.model.BiometricsEvent
import com.example.feature_authentication.presentation.biometric.mvi.reducer.BiometricsReducer
import com.example.mvi.MviStore
import com.example.mvi.StateMachine
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class BiometricsViewModel @Inject constructor(
    private val mapper: BiometricsMapper,
    private val sideEffectHandler: BiometricsSideEffectHandler,
    private val reducer: BiometricsReducer,
) : BaseViewModel() {
    private val uiEvent = MutableSharedFlow<BiometricsEvent>(replay = Int.MAX_VALUE)

    private val _uiState = MutableStateFlow(reducer.getInitialState())
    val uiState = _uiState.asStateFlow().mapData(
        coroutineScope = viewModelScope,
        mapper = { domainState -> mapper.map(domainState) },
    )

    init {
        createMvi()
    }

    fun onEvent(event: BiometricsEvent) {
        viewModelScope.launch { uiEvent.emit(event) }
    }

    private fun createMvi() {
        val mviStore = MviStore(
            stateMachine = StateMachine(
                reducer,
                reducer.getInitialState(),
            ),
            sideEffectHandler = sideEffectHandler,
        )
        mviStore.start(
            coroutineScope = viewModelScope,
            actionState = { state ->
                _uiState.update { state }
            },
            actionUiCommand = {},
        )

        uiEvent
            .onEach(mviStore::onEvent)
            .launchIn(viewModelScope)
    }
}
