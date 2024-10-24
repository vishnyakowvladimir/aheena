package com.example.feature_authentication.presentation.login

import androidx.lifecycle.viewModelScope
import com.example.core.extensions.mapData
import com.example.core.presentation.base.BaseViewModel
import com.example.feature_authentication.presentation.login.mapper.LoginMapper
import com.example.feature_authentication.presentation.login.mvi.handler.LoginSideEffectHandler
import com.example.feature_authentication.presentation.login.mvi.model.LoginEvent
import com.example.feature_authentication.presentation.login.mvi.reducer.LoginReducer
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

internal class LoginViewModel @Inject constructor(
    private val mapper: LoginMapper,
    private val sideEffectHandler: LoginSideEffectHandler,
    private val reducer: LoginReducer,
) : BaseViewModel() {
    private val uiEvent = MutableSharedFlow<LoginEvent>(replay = Int.MAX_VALUE)

    private val _uiState = MutableStateFlow(reducer.getInitialState())
    val uiState = _uiState.asStateFlow().mapData(
        coroutineScope = viewModelScope,
        mapper = { domainState -> mapper.map(domainState) },
    )

    init {
        createMvi()
    }

    fun onEvent(event: LoginEvent) {
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
