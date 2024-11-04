package com.example.feature_main.presentation.main.mvi.reducer

import com.example.feature_main.presentation.main.mvi.model.MainDomainState
import com.example.feature_main.presentation.main.mvi.model.MainEvent
import com.example.feature_main.presentation.main.mvi.model.MainSideEffect
import com.example.feature_main.presentation.main.mvi.model.MainUiCommand
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class MainDomainReducer @Inject constructor() :
    Reducer<MainEvent.Domain, MainDomainState, MainSideEffect, MainUiCommand> {

    override fun update(
        state: MainDomainState,
        event: MainEvent.Domain,
    ): Update<MainDomainState, MainSideEffect, MainUiCommand> {
        return Update.nothing()
    }
}

