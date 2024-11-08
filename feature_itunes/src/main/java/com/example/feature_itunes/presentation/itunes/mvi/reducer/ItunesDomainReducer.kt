package com.example.feature_itunes.presentation.itunes.mvi.reducer

import com.example.feature_itunes.presentation.itunes.mvi.model.ItunesDomainState
import com.example.feature_itunes.presentation.itunes.mvi.model.ItunesEvent
import com.example.feature_itunes.presentation.itunes.mvi.model.ItunesSideEffect
import com.example.feature_itunes.presentation.itunes.mvi.model.ItunesUiCommand
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

private const val LOAD_ITEMS_LIMIT = 20

internal class ItunesDomainReducer @Inject constructor() :
    Reducer<ItunesEvent.Domain, ItunesDomainState, ItunesSideEffect, ItunesUiCommand> {

    override fun update(
        state: ItunesDomainState,
        event: ItunesEvent.Domain,
    ): Update<ItunesDomainState, ItunesSideEffect, ItunesUiCommand> {
        return when (event) {
            is ItunesEvent.Domain.LoadDataNeeded -> reduceLoadDataNeeded(state)
            is ItunesEvent.Domain.OnDataLoaded -> reduceOnDataLoaded(state, event)
        }
    }

    private fun reduceLoadDataNeeded(
        state: ItunesDomainState,
    ): Update<ItunesDomainState, ItunesSideEffect, ItunesUiCommand> {
        if (state.isLoading || state.isAllLoaded) {
            return Update.nothing()
        }

        val page = state.page

        return Update.stateWithSideEffects(
            state = state.copy(
                isLoading = true,
                page = page.inc(),
            ),
            sideEffects = listOf(
                ItunesSideEffect.Domain.LoadData(
                    offset = page * LOAD_ITEMS_LIMIT,
                    limit = LOAD_ITEMS_LIMIT,
                    term = "Often overlooked",
                )
            )
        )
    }

    private fun reduceOnDataLoaded(
        state: ItunesDomainState,
        event: ItunesEvent.Domain.OnDataLoaded,
    ): Update<ItunesDomainState, ItunesSideEffect, ItunesUiCommand> {
        val currentList = state.tracks
        val loadedList = event.tracks.filter { track -> track.name.isNotEmpty() }
        val allList = currentList + loadedList

        return Update.state(
            state.copy(
                isAllLoaded = loadedList.isEmpty(),
                isLoading = false,
                isError = false,
                isShowLoading = false,
                tracks = allList,
            )
        )
    }
}

