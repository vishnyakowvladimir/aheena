package com.example.feature_authentication.presentation.create_pin.mvi.reducer

import com.example.feature_authentication.biometric.BiometricController
import com.example.feature_authentication.biometric.model.BiometricAuthenticators
import com.example.feature_authentication.presentation.create_pin.mvi.model.CreatePinDomainState
import com.example.feature_authentication.presentation.create_pin.mvi.model.CreatePinEvent
import com.example.feature_authentication.presentation.create_pin.mvi.model.CreatePinSideEffect
import com.example.feature_authentication.presentation.create_pin.mvi.model.CreatePinUiCommand
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class CreatePinDomainReducer @Inject constructor(
    private val biometricController: BiometricController,
) :
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
        return if (biometricController.canAuthenticate(BiometricAuthenticators.STRONG).isSuccess) {
            Update.sideEffects(listOf(CreatePinSideEffect.Ui.OpenBiometricScreen))
        } else {
            Update.sideEffects(listOf(CreatePinSideEffect.Ui.OpenMainScreen))
        }
    }
}

