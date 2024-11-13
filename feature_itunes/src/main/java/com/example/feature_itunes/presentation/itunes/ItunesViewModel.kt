package com.example.feature_itunes.presentation.itunes

import androidx.lifecycle.viewModelScope
import com.example.core_api.presentation.base.BaseViewModel
import com.example.core_api.utils.extension.mapData
import com.example.feature_itunes.presentation.itunes.mapper.ItunesMapper
import com.example.feature_itunes.presentation.itunes.mvi.handler.ItunesSideEffectHandler
import com.example.feature_itunes.presentation.itunes.mvi.model.ItunesEvent
import com.example.feature_itunes.presentation.itunes.mvi.reducer.ItunesReducer
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

internal class ItunesViewModel @Inject constructor(
    private val mapper: ItunesMapper,
    private val sideEffectHandler: ItunesSideEffectHandler,
    private val reducer: ItunesReducer,
) : BaseViewModel() {
    private val uiEvent = MutableSharedFlow<ItunesEvent>(replay = Int.MAX_VALUE)

    private val _uiState = MutableStateFlow(reducer.getInitialState())
    val uiState = _uiState.asStateFlow().mapData(
        coroutineScope = viewModelScope,
        mapper = { domainState -> mapper.map(domainState) },
    )

    init {
        createMvi()
        onEvent(ItunesEvent.Domain.LoadDataNeeded)
    }

    fun onEvent(event: ItunesEvent) {
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
