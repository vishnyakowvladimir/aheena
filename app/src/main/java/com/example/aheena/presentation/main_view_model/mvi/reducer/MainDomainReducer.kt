package com.example.aheena.presentation.main_view_model.mvi.reducer

import com.example.aheena.presentation.main_view_model.mvi.model.MainCommand
import com.example.aheena.presentation.main_view_model.mvi.model.MainDomainState
import com.example.aheena.presentation.main_view_model.mvi.model.MainEvent
import com.example.aheena.presentation.main_view_model.mvi.model.MainSideEffect
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class MainDomainReducer @Inject constructor() :
    Reducer<MainEvent.Domain, MainDomainState, MainSideEffect, MainCommand> {

    override fun update(
        state: MainDomainState,
        event: MainEvent.Domain
    ): Update<MainDomainState, MainSideEffect, MainCommand> {
        return when (event) {
            is MainEvent.Domain.OnLoadAppThemeNeeded -> reduceOnLoadAppThemeNeeded()
            is MainEvent.Domain.OnAppThemeLoaded -> reduceOnAppThemeLoaded(state, event)
        }
    }

    private fun reduceOnLoadAppThemeNeeded(): Update<MainDomainState, MainSideEffect, MainCommand> {
        return Update.commands(MainCommand.LoadAppTheme)
    }

    private fun reduceOnAppThemeLoaded(
        state: MainDomainState,
        event: MainEvent.Domain.OnAppThemeLoaded,
    ): Update<MainDomainState, MainSideEffect, MainCommand> {
        return Update.state(
            state.copy(
                themeState = MainDomainState.ThemeState(
                    themeMode = event.data.themeMode,
                    viewScale = event.data.viewScale,
                ),
            )
        )
    }
}

