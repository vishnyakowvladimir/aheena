package com.example.feature_authentication.presentation.create_pin.mvi.reducer

import com.example.feature_authentication.presentation.create_pin.mvi.model.CreatePinDomainState
import com.example.feature_authentication.presentation.create_pin.mvi.model.CreatePinEvent
import com.example.feature_authentication.presentation.create_pin.mvi.model.CreatePinSideEffect
import com.example.feature_authentication.presentation.create_pin.mvi.model.CreatePinUiCommand
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class CreatePinReducer @Inject constructor(
    private val uiReducer: CreatePinUiReducer,
    private val domainReducer: CreatePinDomainReducer,
) : Reducer<CreatePinEvent, CreatePinDomainState, CreatePinSideEffect, CreatePinUiCommand> {

    override fun update(
        state: CreatePinDomainState,
        event: CreatePinEvent,
    ): Update<CreatePinDomainState, CreatePinSideEffect, CreatePinUiCommand> {
        return when (event) {
            is CreatePinEvent.None -> Update.nothing()
            is CreatePinEvent.Ui -> uiReducer.update(state, event)
            is CreatePinEvent.Domain -> domainReducer.update(state, event)
        }
    }

    fun getInitialState(): CreatePinDomainState {
        return CreatePinDomainState(
            createPinState = CreatePinDomainState.CreatePinState(
                pin = emptyList()
            ),
            confirmPinState = CreatePinDomainState.ConfirmPinState(
                pin = emptyList()
            ),
            mode = CreatePinDomainState.Mode.CREATE,
            isError = false,
        )
    }
}
