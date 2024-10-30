package com.example.feature_authentication.presentation.pin.mvi.reducer

import com.example.feature_authentication.presentation.pin.mvi.model.PinDomainState
import com.example.feature_authentication.presentation.pin.mvi.model.PinEvent
import com.example.feature_authentication.presentation.pin.mvi.model.PinSideEffect
import com.example.feature_authentication.presentation.pin.mvi.model.PinUiCommand
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class PinReducer @Inject constructor(
    private val uiReducer: PinUiReducer,
    private val domainReducer: PinDomainReducer,
) : Reducer<PinEvent, PinDomainState, PinSideEffect, PinUiCommand> {

    override fun update(
        state: PinDomainState,
        event: PinEvent,
    ): Update<PinDomainState, PinSideEffect, PinUiCommand> {
        return when (event) {
            is PinEvent.Ui -> uiReducer.update(state, event)
            is PinEvent.Domain -> domainReducer.update(state, event)
        }
    }

    fun getInitialState(): PinDomainState {
        return PinDomainState(
            pin = emptyList(),
        )
    }
}
