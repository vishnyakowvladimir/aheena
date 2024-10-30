package com.example.feature_authentication.presentation.biometric.mvi.reducer

import com.example.feature_authentication.presentation.biometric.mvi.model.BiometricsDomainState
import com.example.feature_authentication.presentation.biometric.mvi.model.BiometricsEvent
import com.example.feature_authentication.presentation.biometric.mvi.model.BiometricsSideEffect
import com.example.feature_authentication.presentation.biometric.mvi.model.BiometricsUiCommand
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class BiometricsDomainReducer @Inject constructor() :
    Reducer<BiometricsEvent.Domain, BiometricsDomainState, BiometricsSideEffect, BiometricsUiCommand> {

    override fun update(
        state: BiometricsDomainState,
        event: BiometricsEvent.Domain
    ): Update<BiometricsDomainState, BiometricsSideEffect, BiometricsUiCommand> {
        return Update.nothing()
    }
}

