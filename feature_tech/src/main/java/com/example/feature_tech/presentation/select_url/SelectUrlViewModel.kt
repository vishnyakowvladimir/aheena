package com.example.feature_tech.presentation.select_url

import androidx.lifecycle.viewModelScope
import com.example.core_api.presentation.base.BaseViewModel
import com.example.core_api.utils.extension.mapData
import com.example.feature_tech.presentation.select_url.mapper.SelectUrlMapper
import com.example.feature_tech.presentation.select_url.mvi.handler.SelectUrlSideEffectHandler
import com.example.feature_tech.presentation.select_url.mvi.model.SelectUrlEvent
import com.example.feature_tech.presentation.select_url.mvi.reducer.SelectUrlReducer
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

internal class SelectUrlViewModel @Inject constructor(
    private val mapper: SelectUrlMapper,
    private val sideEffectHandler: SelectUrlSideEffectHandler,
    private val reducer: SelectUrlReducer,
) : BaseViewModel() {
    private val uiEvent = MutableSharedFlow<SelectUrlEvent>(replay = Int.MAX_VALUE)

    private val _uiState = MutableStateFlow(reducer.getInitialState())
    val uiState = _uiState.asStateFlow().mapData(
        coroutineScope = viewModelScope,
        mapper = { domainState -> mapper.map(domainState) },
    )

    init {
        createMvi()
    }

    fun onEvent(event: SelectUrlEvent) {
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
