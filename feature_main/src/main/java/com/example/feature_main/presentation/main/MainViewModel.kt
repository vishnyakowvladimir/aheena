package com.example.feature_main.presentation.main

import androidx.lifecycle.viewModelScope
import com.example.core_api.presentation.base.BaseViewModel
import com.example.core_api.utils.extension.mapData
import com.example.feature_main.presentation.main.mapper.MainMapper
import com.example.feature_main.presentation.main.mvi.handler.MainSideEffectHandler
import com.example.feature_main.presentation.main.mvi.model.MainEvent
import com.example.feature_main.presentation.main.mvi.reducer.MainReducer
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

internal class MainViewModel @Inject constructor(
    private val mapper: MainMapper,
    private val sideEffectHandler: MainSideEffectHandler,
    private val reducer: MainReducer,
) : BaseViewModel() {
    private val uiEvent = MutableSharedFlow<MainEvent>(replay = Int.MAX_VALUE)

    private val _uiState = MutableStateFlow(reducer.getInitialState())
    val uiState = _uiState.asStateFlow().mapData(
        coroutineScope = viewModelScope,
        mapper = { domainState -> mapper.map(domainState) },
    )

    init {
        createMvi()
    }

    fun onEvent(event: MainEvent) {
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
