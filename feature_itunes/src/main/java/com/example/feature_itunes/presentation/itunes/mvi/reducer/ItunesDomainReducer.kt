package com.example.feature_itunes.presentation.itunes.mvi.reducer

import com.example.core_api.network.model.ApiResult
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
        if (state.isTracksLoading || state.isAllLoaded) {
            return Update.nothing()
        }

        val page = state.page

        return Update.stateWithSideEffects(
            state = state.copy(
                isTracksLoading = true,
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
        return when (val result = event.result) {
            is ApiResult.Error -> {
                Update.state(
                    state.copy(
                        isAllLoaded = false,
                        isTracksLoading = false,
                        showLoading = false,
                        isError = true,
                        tracks = emptyList(),
                    )
                )
            }

            is ApiResult.Success -> {
                val currentList = state.tracks
                val loadedList = result.data.filter { track -> track.name.isNotEmpty() }
                val allList = currentList + loadedList

                Update.state(
                    state.copy(
                        isAllLoaded = loadedList.isEmpty(),
                        isTracksLoading = false,
                        isError = false,
                        showLoading = false,
                        tracks = allList,
                    )
                )
            }
        }

    }
}

