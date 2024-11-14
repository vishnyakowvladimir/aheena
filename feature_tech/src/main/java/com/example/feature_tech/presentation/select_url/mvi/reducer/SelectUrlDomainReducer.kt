package com.example.feature_tech.presentation.select_url.mvi.reducer

import com.example.feature_tech.presentation.select_url.mvi.model.SelectUrlDomainState
import com.example.feature_tech.presentation.select_url.mvi.model.SelectUrlEvent
import com.example.feature_tech.presentation.select_url.mvi.model.SelectUrlSideEffect
import com.example.feature_tech.presentation.select_url.mvi.model.SelectUrlUiCommand
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class SelectUrlDomainReducer @Inject constructor() :
    Reducer<SelectUrlEvent.Domain, SelectUrlDomainState, SelectUrlSideEffect, SelectUrlUiCommand> {

    override fun update(
        state: SelectUrlDomainState,
        event: SelectUrlEvent.Domain,
    ): Update<SelectUrlDomainState, SelectUrlSideEffect, SelectUrlUiCommand> {
        return Update.nothing()
    }
}

