package com.example.feature_main.presentation.main.mvi.reducer

import com.example.feature_main.presentation.main.mvi.model.MainDomainState
import com.example.feature_main.presentation.main.mvi.model.MainEvent
import com.example.feature_main.presentation.main.mvi.model.MainSideEffect
import com.example.feature_main.presentation.main.mvi.model.MainUiCommand
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class MainReducer @Inject constructor(
    private val uiReducer: MainUiReducer,
    private val domainReducer: MainDomainReducer,
) : Reducer<MainEvent, MainDomainState, MainSideEffect, MainUiCommand> {

    override fun update(
        state: MainDomainState,
        event: MainEvent,
    ): Update<MainDomainState, MainSideEffect, MainUiCommand> {
        return when (event) {
            is MainEvent.Ui -> uiReducer.update(state, event)
            is MainEvent.Domain -> domainReducer.update(state, event)
        }
    }

    fun getInitialState(): MainDomainState {
        return MainDomainState()
    }
}
