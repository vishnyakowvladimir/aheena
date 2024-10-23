package com.example.aheena.presentation.main_view_model.mvi.reducer

import com.example.aheena.presentation.main_view_model.mvi.model.MainDomainState
import com.example.aheena.presentation.main_view_model.mvi.model.MainEvent
import com.example.aheena.presentation.main_view_model.mvi.model.MainSideEffect
import com.example.aheena.presentation.main_view_model.mvi.model.MainUiCommand
import com.example.core.presentation.theme_manager.ThemeManager
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class MainReducer @Inject constructor(
    private val uiReducer: MainUiReducer,
    private val domainReducer: MainDomainReducer,
    private val themeManager: ThemeManager,
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
        return MainDomainState(
            themeState = MainDomainState.ThemeState(
                viewScale = themeManager.getScale(),
            )
        )
    }
}
