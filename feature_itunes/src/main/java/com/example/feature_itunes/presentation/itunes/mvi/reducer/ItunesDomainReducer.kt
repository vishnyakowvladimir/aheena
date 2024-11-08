package com.example.feature_itunes.presentation.itunes.mvi.reducer

import com.example.feature_itunes.presentation.itunes.mvi.model.ItunesDomainState
import com.example.feature_itunes.presentation.itunes.mvi.model.ItunesEvent
import com.example.feature_itunes.presentation.itunes.mvi.model.ItunesSideEffect
import com.example.feature_itunes.presentation.itunes.mvi.model.ItunesUiCommand
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class ItunesDomainReducer @Inject constructor() :
    Reducer<ItunesEvent.Domain, ItunesDomainState, ItunesSideEffect, ItunesUiCommand> {

    override fun update(
        state: ItunesDomainState,
        event: ItunesEvent.Domain,
    ): Update<ItunesDomainState, ItunesSideEffect, ItunesUiCommand> {
        return when (event) {
            is ItunesEvent.Domain.LoadDataNeeded -> reduceLoadData()
            is ItunesEvent.Domain.OnDataLoaded -> reduceOnDataLoaded(state, event)
        }
    }

    private fun reduceLoadData(): Update<ItunesDomainState, ItunesSideEffect, ItunesUiCommand> {
        return Update.sideEffects(
            listOf(
                ItunesSideEffect.Domain.LoadData(
                    offset = 0,
                    limit = 10,
                    term = "Often overlooked",
                )
            )
        )
    }

    private fun reduceOnDataLoaded(
        state: ItunesDomainState,
        event: ItunesEvent.Domain.OnDataLoaded,
    ): Update<ItunesDomainState, ItunesSideEffect, ItunesUiCommand> {
        return Update.state(
            state.copy(
                isError = false,
                isLoading = false,
                tracks = event.tracks.filter { track -> track.name.isNotEmpty() },
            )
        )
    }
}

