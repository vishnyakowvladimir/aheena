package com.example.feature_tech.presentation.select_url.mvi.reducer

import com.example.feature_tech.presentation.select_url.mvi.model.SelectUrlDomainState
import com.example.feature_tech.presentation.select_url.mvi.model.SelectUrlEvent
import com.example.feature_tech.presentation.select_url.mvi.model.SelectUrlSideEffect
import com.example.feature_tech.presentation.select_url.mvi.model.SelectUrlUiCommand
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class SelectUrlUiReducer @Inject constructor() :
    Reducer<SelectUrlEvent.Ui, SelectUrlDomainState, SelectUrlSideEffect, SelectUrlUiCommand> {

    override fun update(
        state: SelectUrlDomainState,
        event: SelectUrlEvent.Ui,
    ): Update<SelectUrlDomainState, SelectUrlSideEffect, SelectUrlUiCommand> {
        return when (event) {
            is SelectUrlEvent.Ui.OnBackPressed -> reduceOnBackPressed()
            is SelectUrlEvent.Ui.OnUrlsTypeClick -> reduceOnUrlsTypeClick(state, event)
        }
    }

    private fun reduceOnBackPressed(): Update<SelectUrlDomainState, SelectUrlSideEffect, SelectUrlUiCommand> {
        return Update.sideEffects(
            sideEffects = listOf(SelectUrlSideEffect.Ui.Back),
        )
    }

    private fun reduceOnUrlsTypeClick(
        state: SelectUrlDomainState,
        event: SelectUrlEvent.Ui.OnUrlsTypeClick,
    ): Update<SelectUrlDomainState, SelectUrlSideEffect, SelectUrlUiCommand> {
        return Update.stateWithSideEffects(
            state = state.copy(urlsType = event.urlsType),
            sideEffects = listOf(SelectUrlSideEffect.Domain.SaveUrlsType(event.urlsType)),
        )
    }
}
