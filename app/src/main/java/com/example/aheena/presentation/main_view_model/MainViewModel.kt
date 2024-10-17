package com.example.aheena.presentation.main_view_model

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.aheena.presentation.main_view_model.mapper.MainMapper
import com.example.aheena.presentation.main_view_model.mvi.handler.MainCommandHandler
import com.example.aheena.presentation.main_view_model.mvi.handler.MainSideEffectHandler
import com.example.aheena.presentation.main_view_model.mvi.model.MainEvent
import com.example.aheena.presentation.main_view_model.mvi.model.MainUiCommand
import com.example.aheena.presentation.main_view_model.mvi.reducer.MainReducer
import com.example.core.extensions.mapData
import com.example.core.presentation.base.BaseViewModel
import com.example.mvi.MviStore
import com.example.mvi.StateMachine
import kotlinx.coroutines.channels.BufferOverflow
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
    private val commandHandler: MainCommandHandler,
    private val reducer: MainReducer,
    private val navController: NavHostController,
) : BaseViewModel() {
    private val uiEvent = MutableSharedFlow<MainEvent>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    private val _uiState = MutableStateFlow(reducer.getInitialState())
    val uiState = _uiState.asStateFlow().mapData(
        coroutineScope = viewModelScope,
        mapper = { domainState -> mapper.map(domainState) },
    )

    init {
        createMvi()
        onEvent(MainEvent.Domain.OnLoadAppThemeNeeded)
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
            commandHandler = commandHandler,
        )
        mviStore.start(
            coroutineScope = viewModelScope,
            actionState = { state ->
                _uiState.update { state }
            },
            actionSideEffect = { sideEffect ->
                when (val uiSideEffect = sideEffectHandler.createUiSideEffect(sideEffect)) {
                    is MainUiCommand.Command -> {}
                    is MainUiCommand.Navigation -> handleNavigation(uiSideEffect)
                }
            }
        )

        uiEvent
            .onEach(mviStore::onEvent)
            .launchIn(viewModelScope)
    }

    private fun handleNavigation(navigationCommand: MainUiCommand.Navigation) {
        when (navigationCommand) {
            MainUiCommand.Navigation.Back -> navController.popBackStack()
        }
    }
}
