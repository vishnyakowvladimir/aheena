package com.example.feature_itunes.presentation.itunes.mvi.reducer

import com.example.feature_itunes.presentation.itunes.mvi.model.ItunesDomainState
import com.example.feature_itunes.presentation.itunes.mvi.model.ItunesEvent
import com.example.feature_itunes.presentation.itunes.mvi.model.ItunesSideEffect
import com.example.feature_itunes.presentation.itunes.mvi.model.ItunesUiCommand
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class ItunesUiReducer @Inject constructor() :
    Reducer<ItunesEvent.Ui, ItunesDomainState, ItunesSideEffect, ItunesUiCommand> {

    override fun update(
        state: ItunesDomainState,
        event: ItunesEvent.Ui,
    ): Update<ItunesDomainState, ItunesSideEffect, ItunesUiCommand> {
        return when (event) {
            is ItunesEvent.Ui.OnBackPressed -> reduceOnBackPressed()
        }
    }

    private fun reduceOnBackPressed(): Update<ItunesDomainState, ItunesSideEffect, ItunesUiCommand> {
        return Update.sideEffects(
            sideEffects = listOf(ItunesSideEffect.Ui.Back),
        )
    }
}
