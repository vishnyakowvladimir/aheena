package com.example.aheena.presentation.main_view_model.mvi.reducer

import com.example.aheena.R
import com.example.aheena.presentation.main_view_model.mvi.model.MainDomainState
import com.example.aheena.presentation.main_view_model.mvi.model.MainEvent
import com.example.aheena.presentation.main_view_model.mvi.model.MainSideEffect
import com.example.aheena.presentation.main_view_model.mvi.model.MainUiCommand
import com.example.core_api.utils.string_provider.StringProvider
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class MainDomainReducer @Inject constructor(
    private val stringProvider: StringProvider,
) :
    Reducer<MainEvent.Domain, MainDomainState, MainSideEffect, MainUiCommand> {

    override fun update(
        state: MainDomainState,
        event: MainEvent.Domain
    ): Update<MainDomainState, MainSideEffect, MainUiCommand> {
        return when (event) {
            is MainEvent.Domain.OnLoadDataNeeded -> reduceOnLoadDataNeeded()
            is MainEvent.Domain.OnDataLoaded -> reduceOnDataLoaded(state)
            is MainEvent.Domain.OnScaleChanged -> reduceOnScaleChanged(state, event)
            is MainEvent.Domain.OnNoInternetConnection -> reduceOnNoInternetConnection()
        }
    }

    private fun reduceOnLoadDataNeeded(): Update<MainDomainState, MainSideEffect, MainUiCommand> {
        return Update.sideEffects(
            sideEffects = listOf(MainSideEffect.Domain.LoadData),
        )
    }

    private fun reduceOnDataLoaded(state: MainDomainState): Update<MainDomainState, MainSideEffect, MainUiCommand> {
        return if (state.deeplinkState != null) {
            val uri = state.deeplinkState.uri

            Update.stateWithSideEffects(
                state = state.copy(
                    data = "data",
                    deeplinkState = null,
                ),
                sideEffects = listOf(MainSideEffect.Ui.HandleDeeplink(uri)),
            )
        } else {
            Update.stateWithSideEffects(
                state = state.copy(
                    data = "data",
                ),
                sideEffects = listOf(MainSideEffect.Ui.OpenAuthentication),
            )
        }
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

    private fun reduceOnNoInternetConnection(): Update<MainDomainState, MainSideEffect, MainUiCommand> {
        return Update.uiCommands(
            listOf(
                MainUiCommand.ShowSnackbar(stringProvider.getString(R.string.connectivity_error)),
            ),
        )
    }
}

