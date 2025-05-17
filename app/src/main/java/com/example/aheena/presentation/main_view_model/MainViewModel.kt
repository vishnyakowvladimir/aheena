package com.example.aheena.presentation.main_view_model

import androidx.lifecycle.viewModelScope
import com.example.aheena.presentation.main_view_model.mapper.MainMapper
import com.example.aheena.presentation.main_view_model.mvi.handler.MainSideEffectHandler
import com.example.aheena.presentation.main_view_model.mvi.model.MainEvent
import com.example.aheena.presentation.main_view_model.mvi.model.MainUiCommand
import com.example.aheena.presentation.main_view_model.mvi.reducer.MainReducer
import com.example.core_api.presentation.base.BaseViewModel
import com.example.core_api.utils.eventbus.AppEventBus
import com.example.core_api.utils.eventbus.model.AppEvent
import com.example.core_api.utils.extension.mapData
import com.example.mvi.MviStore
import com.example.mvi.StateMachine
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class MainViewModel @Inject constructor(
    private val mapper: MainMapper,
    private val sideEffectHandler: MainSideEffectHandler,
    private val reducer: MainReducer,
    private val eventBus: AppEventBus,
) : BaseViewModel() {

    private val uiEvent = MutableSharedFlow<MainEvent>(replay = Int.MAX_VALUE)

    private val _uiState = MutableStateFlow(reducer.getInitialState())
    val uiState = _uiState.asStateFlow().mapData(
        coroutineScope = viewModelScope,
        mapper = { domainState -> mapper.map(domainState) },
    )

    private val _uiCommand = Channel<MainUiCommand>()
    val uiCommand = _uiCommand.receiveAsFlow()

    init {
        createMvi()
        subscribe()
        onEvent(MainEvent.Ui.OnApplyThemeNeeded)
    }

    fun onEvent(event: MainEvent) {
        viewModelScope.launch { uiEvent.emit(event) }
    }

    private fun subscribe() {
        eventBus.sharedFlow
            .onEach { event ->
                when (event) {
                    is AppEvent.OnNoInternetConnection -> {
                        onEvent(MainEvent.Domain.OnNoInternetConnection)
                    }
                }
            }
            .launchIn(viewModelScope)
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
            actionUiCommand = { command ->
                _uiCommand.send(command)
            },
        )

        uiEvent
            .onEach(mviStore::onEvent)
            .launchIn(viewModelScope)
    }
}
