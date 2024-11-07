package com.example.feature_itunes.presentation.itunes.mvi.reducer

import com.example.feature_itunes.presentation.itunes.mvi.model.ItunesDomainState
import com.example.feature_itunes.presentation.itunes.mvi.model.ItunesEvent
import com.example.feature_itunes.presentation.itunes.mvi.model.ItunesSideEffect
import com.example.feature_itunes.presentation.itunes.mvi.model.ItunesUiCommand
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class ItunesReducer @Inject constructor(
    private val uiReducer: ItunesUiReducer,
    private val domainReducer: ItunesDomainReducer,
) : Reducer<ItunesEvent, ItunesDomainState, ItunesSideEffect, ItunesUiCommand> {

    override fun update(
        state: ItunesDomainState,
        event: ItunesEvent,
    ): Update<ItunesDomainState, ItunesSideEffect, ItunesUiCommand> {
        return when (event) {
            is ItunesEvent.None -> Update.nothing()
            is ItunesEvent.Ui -> uiReducer.update(state, event)
            is ItunesEvent.Domain -> domainReducer.update(state, event)
        }
    }

    fun getInitialState(): ItunesDomainState {
        return ItunesDomainState()
    }
}
