package com.example.aheena.presentation.main_view_model.mvi.reducer

import com.example.aheena.presentation.main_view_model.mvi.model.MainDomainState
import com.example.aheena.presentation.main_view_model.mvi.model.MainEvent
import com.example.aheena.presentation.main_view_model.mvi.model.MainSideEffect
import com.example.aheena.presentation.main_view_model.mvi.model.MainUiCommand
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class MainDomainReducer @Inject constructor() :
    Reducer<MainEvent.Domain, MainDomainState, MainSideEffect, MainUiCommand> {

    override fun update(
        state: MainDomainState,
        event: MainEvent.Domain
    ): Update<MainDomainState, MainSideEffect, MainUiCommand> {
        return when (event) {
            is MainEvent.Domain.OnLoadAppThemeNeeded -> reduceOnLoadAppThemeNeeded()
            is MainEvent.Domain.OnAppThemeLoaded -> reduceOnAppThemeLoaded(state, event)
        }
    }

    private fun reduceOnLoadAppThemeNeeded(): Update<MainDomainState, MainSideEffect, MainUiCommand> {
        return Update.sideEffects(
            listOf(MainSideEffect.Domain.LoadAppTheme),
        )
    }

    private fun reduceOnAppThemeLoaded(
        state: MainDomainState,
        event: MainEvent.Domain.OnAppThemeLoaded,
    ): Update<MainDomainState, MainSideEffect, MainUiCommand> {
        return Update.stateWithSideEffects(
            state = state.copy(
                themeState = MainDomainState.ThemeState(
                    themeMode = event.data.themeMode,
                    viewScale = event.data.viewScale,
                ),
            ),
            sideEffects = listOf(MainSideEffect.Ui.OpenAuthentication),
        )
    }
}

