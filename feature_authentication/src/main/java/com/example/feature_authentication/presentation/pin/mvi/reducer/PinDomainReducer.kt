package com.example.feature_authentication.presentation.pin.mvi.reducer

import com.example.feature_authentication.presentation.pin.mvi.model.PinDomainState
import com.example.feature_authentication.presentation.pin.mvi.model.PinEvent
import com.example.feature_authentication.presentation.pin.mvi.model.PinSideEffect
import com.example.feature_authentication.presentation.pin.mvi.model.PinUiCommand
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class PinDomainReducer @Inject constructor() :
    Reducer<PinEvent.Domain, PinDomainState, PinSideEffect, PinUiCommand> {

    override fun update(
        state: PinDomainState,
        event: PinEvent.Domain
    ): Update<PinDomainState, PinSideEffect, PinUiCommand> {
        return Update.nothing()
    }
}

