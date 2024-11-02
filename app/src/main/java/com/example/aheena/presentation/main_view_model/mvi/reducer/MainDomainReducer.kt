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
            is MainEvent.Domain.None -> Update.nothing()
            is MainEvent.Domain.OnLoadDataNeeded -> reduceOnLoadDataNeeded()
            is MainEvent.Domain.OnDataLoaded -> reduceOnDataLoaded()
            is MainEvent.Domain.OnScaleChanged -> reduceOnScaleChanged(state, event)
        }
    }

    private fun reduceOnLoadDataNeeded(): Update<MainDomainState, MainSideEffect, MainUiCommand> {
        return Update.sideEffects(
            sideEffects = listOf(MainSideEffect.Domain.LoadData),
        )
    }

    private fun reduceOnDataLoaded(): Update<MainDomainState, MainSideEffect, MainUiCommand> {
        return Update.sideEffects(
            sideEffects = listOf(MainSideEffect.Ui.OpenAuthentication),
        )
    }

    private fun reduceOnScaleChanged(
        state: MainDomainState,
        event: MainEvent.Domain.OnScaleChanged,
    ): Update<MainDomainState, MainSideEffect, MainUiCommand> {
        val themeState = state.themeState
        val updatedThemeState = themeState.copy(
            viewScale = event.scale,
        )

        return Update.state(
            state = state.copy(
                themeState = updatedThemeState,
            ),
        )
    }
}

