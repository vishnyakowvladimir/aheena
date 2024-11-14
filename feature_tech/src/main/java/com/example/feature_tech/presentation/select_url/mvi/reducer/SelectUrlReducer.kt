package com.example.feature_tech.presentation.select_url.mvi.reducer

import com.example.data_source_api.storage.network.UrlProvider
import com.example.feature_tech.presentation.select_url.mvi.model.SelectUrlDomainState
import com.example.feature_tech.presentation.select_url.mvi.model.SelectUrlEvent
import com.example.feature_tech.presentation.select_url.mvi.model.SelectUrlSideEffect
import com.example.feature_tech.presentation.select_url.mvi.model.SelectUrlUiCommand
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class SelectUrlReducer @Inject constructor(
    private val uiReducer: SelectUrlUiReducer,
    private val domainReducer: SelectUrlDomainReducer,
    private val urlProvider: UrlProvider,
) : Reducer<SelectUrlEvent, SelectUrlDomainState, SelectUrlSideEffect, SelectUrlUiCommand> {

    override fun update(
        state: SelectUrlDomainState,
        event: SelectUrlEvent,
    ): Update<SelectUrlDomainState, SelectUrlSideEffect, SelectUrlUiCommand> {
        return when (event) {
            is SelectUrlEvent.None -> Update.nothing()
            is SelectUrlEvent.Ui -> uiReducer.update(state, event)
            is SelectUrlEvent.Domain -> domainReducer.update(state, event)
        }
    }

    fun getInitialState(): SelectUrlDomainState {
        return SelectUrlDomainState(
            urlsType = urlProvider.selectedUrlsType,
        )
    }
}
