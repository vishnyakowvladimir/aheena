package com.example.feature_authentication.presentation.biometric.mvi.reducer

import com.example.feature_authentication.presentation.biometric.mvi.model.BiometricsDomainState
import com.example.feature_authentication.presentation.biometric.mvi.model.BiometricsEvent
import com.example.feature_authentication.presentation.biometric.mvi.model.BiometricsSideEffect
import com.example.feature_authentication.presentation.biometric.mvi.model.BiometricsUiCommand
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class BiometricsUiReducer @Inject constructor() :
    Reducer<BiometricsEvent.Ui, BiometricsDomainState, BiometricsSideEffect, BiometricsUiCommand> {

    override fun update(
        state: BiometricsDomainState,
        event: BiometricsEvent.Ui,
    ): Update<BiometricsDomainState, BiometricsSideEffect, BiometricsUiCommand> {
        return when (event) {
            is BiometricsEvent.Ui.None -> Update.nothing()
            is BiometricsEvent.Ui.OnBackPressed -> reduceOnBackPressed()
            is BiometricsEvent.Ui.OnEnableButtonClick -> reduceOnEnableButtonClick()
            is BiometricsEvent.Ui.OnSkipButtonClick -> reduceOnSkipButtonClick()
        }
    }

    private fun reduceOnBackPressed(): Update<BiometricsDomainState, BiometricsSideEffect, BiometricsUiCommand> {
        return Update.sideEffects(
            sideEffects = listOf(BiometricsSideEffect.Ui.Back),
        )
    }

    private fun reduceOnEnableButtonClick(): Update<BiometricsDomainState, BiometricsSideEffect, BiometricsUiCommand> {
        return Update.sideEffects(
            listOf(
                BiometricsSideEffect.Domain.SavePinCode,
                BiometricsSideEffect.Domain.SaveBiometricsFlag(true),
            )
        )
    }

    private fun reduceOnSkipButtonClick(): Update<BiometricsDomainState, BiometricsSideEffect, BiometricsUiCommand> {
        return Update.sideEffects(
            sideEffects = listOf(
                BiometricsSideEffect.Domain.SaveBiometricsFlag(false),
                BiometricsSideEffect.Ui.OpenMainScreen,
            ),
        )
    }
}
