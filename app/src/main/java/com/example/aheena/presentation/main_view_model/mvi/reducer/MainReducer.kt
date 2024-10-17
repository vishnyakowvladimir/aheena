package com.example.aheena.presentation.main_view_model.mvi.reducer

import com.example.aheena.presentation.main_view_model.mvi.model.MainCommand
import com.example.aheena.presentation.main_view_model.mvi.model.MainDomainState
import com.example.aheena.presentation.main_view_model.mvi.model.MainEvent
import com.example.aheena.presentation.main_view_model.mvi.model.MainSideEffect
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class MainReducer @Inject constructor(
    private val uiReducer: MainUiReducer,
    private val domainReducer: MainDomainReducer,
) : Reducer<MainEvent, MainDomainState, MainSideEffect, MainCommand> {

    override fun update(
        state: MainDomainState,
        event: MainEvent,
    ): Update<MainDomainState, MainSideEffect, MainCommand> {
        return when (event) {
            is MainEvent.Ui -> uiReducer.update(state, event)
            is MainEvent.Domain -> domainReducer.update(state, event)
        }
    }

    fun getInitialState(): MainDomainState {
        return MainDomainState()
    }
}
