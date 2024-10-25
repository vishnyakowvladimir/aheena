package com.example.feature_authentication.presentation.create_pin

import androidx.lifecycle.viewModelScope
import com.example.core.presentation.base.BaseViewModel
import com.example.core.utils.extension.mapData
import com.example.feature_authentication.presentation.create_pin.mapper.CreatePinMapper
import com.example.feature_authentication.presentation.create_pin.mvi.handler.CreatePinSideEffectHandler
import com.example.feature_authentication.presentation.create_pin.mvi.model.CreatePinEvent
import com.example.feature_authentication.presentation.create_pin.mvi.reducer.CreatePinReducer
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

internal class CreatePinViewModel @Inject constructor(
    private val mapper: CreatePinMapper,
    private val sideEffectHandler: CreatePinSideEffectHandler,
    private val reducer: CreatePinReducer,
) : BaseViewModel() {
    private val uiEvent = MutableSharedFlow<CreatePinEvent>(replay = Int.MAX_VALUE)

    private val _uiState = MutableStateFlow(reducer.getInitialState())
    val uiState = _uiState.asStateFlow().mapData(
        coroutineScope = viewModelScope,
        mapper = { domainState -> mapper.map(domainState) },
    )

    init {
        createMvi()
    }

    fun onEvent(event: CreatePinEvent) {
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
