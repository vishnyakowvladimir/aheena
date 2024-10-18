package com.example.aheena.presentation.main_view_model

import android.util.Log
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

    private val testStateFlow = MutableStateFlow(0)
    private val testSharedFlow = MutableSharedFlow<Int>(
        replay = 2,
        extraBufferCapacity = 3,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )

    init {
        test()
        createMvi()
        onEvent(MainEvent.Domain.OnLoadAppThemeNeeded)
    }

    private fun test() {
//        viewModelScope.launch {
//            testStateFlow.emit(1)
//            testStateFlow.emit(2)
//            testStateFlow.emit(3)
//            testStateFlow.emit(4)
//            testStateFlow.emit(5)
//
//            testSharedFlow.emit(1)
//            testSharedFlow.emit(2)
//            testSharedFlow.emit(3)
//            testSharedFlow.emit(4)
//            testSharedFlow.emit(5)
//        }
        testStateFlow.tryEmit(1)
        testStateFlow.tryEmit(2)
        testStateFlow.tryEmit(3)
        testStateFlow.tryEmit(4)
        testStateFlow.tryEmit(5)

        testSharedFlow.tryEmit(1)
        testSharedFlow.tryEmit(2)
        testSharedFlow.tryEmit(3)
        testSharedFlow.tryEmit(4)
        testSharedFlow.tryEmit(5)

        viewModelScope.launch {
//            testStateFlow
//                .onEach { value ->
//                    Log.d("check111", "stateFlow: $value")
//                }
//                .launchIn(viewModelScope)
//
//            testSharedFlow
//                .onEach { value ->
//                    Log.d("check111", "sharedFlow: $value")
//                }
//                .launchIn(viewModelScope)

            testStateFlow
                .collect { value ->
                    Log.d("check111", "stateFlow: $value")
                }
        }

        viewModelScope.launch {
            testSharedFlow
                .collect { value ->
                    Log.d("check111", "sharedFlow: $value")
                }
        }
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
