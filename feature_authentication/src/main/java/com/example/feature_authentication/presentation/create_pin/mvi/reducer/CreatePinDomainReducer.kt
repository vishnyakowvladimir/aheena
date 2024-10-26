package com.example.feature_authentication.presentation.create_pin.mvi.reducer

import com.example.feature_authentication.presentation.create_pin.mvi.model.CreatePinDomainState
import com.example.feature_authentication.presentation.create_pin.mvi.model.CreatePinEvent
import com.example.feature_authentication.presentation.create_pin.mvi.model.CreatePinSideEffect
import com.example.feature_authentication.presentation.create_pin.mvi.model.CreatePinUiCommand
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class CreatePinDomainReducer @Inject constructor() :
    Reducer<CreatePinEvent.Domain, CreatePinDomainState, CreatePinSideEffect, CreatePinUiCommand> {

    override fun update(
        state: CreatePinDomainState,
        event: CreatePinEvent.Domain
    ): Update<CreatePinDomainState, CreatePinSideEffect, CreatePinUiCommand> {
        return when (event) {
            is CreatePinEvent.Domain.OnPinCodeSaved -> reduceOnPinCodeSaved()
        }
    }

    private fun reduceOnPinCodeSaved(): Update<CreatePinDomainState, CreatePinSideEffect, CreatePinUiCommand> {
        return Update.sideEffects(listOf(CreatePinSideEffect.Ui.OpenMain))
    }
}

