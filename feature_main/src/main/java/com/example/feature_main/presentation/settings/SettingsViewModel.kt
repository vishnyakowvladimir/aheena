package com.example.feature_main.presentation.settings

import androidx.lifecycle.viewModelScope
import com.example.core.presentation.base.BaseViewModel
import com.example.core.utils.extension.mapData
import com.example.feature_main.presentation.settings.mapper.SettingsMapper
import com.example.feature_main.presentation.settings.mvi.handler.SettingsSideEffectHandler
import com.example.feature_main.presentation.settings.mvi.model.SettingsEvent
import com.example.feature_main.presentation.settings.mvi.reducer.SettingsReducer
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

internal class SettingsViewModel @Inject constructor(
    private val mapper: SettingsMapper,
    private val sideEffectHandler: SettingsSideEffectHandler,
    private val reducer: SettingsReducer,
) : BaseViewModel() {
    private val uiEvent = MutableSharedFlow<SettingsEvent>(replay = Int.MAX_VALUE)

    private val _uiState = MutableStateFlow(reducer.getInitialState())
    val uiState = _uiState.asStateFlow().mapData(
        coroutineScope = viewModelScope,
        mapper = { domainState -> mapper.map(domainState) },
    )

    init {
        createMvi()
    }

    fun onEvent(event: SettingsEvent) {
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
