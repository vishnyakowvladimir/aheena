package com.example.feature_authentication.presentation.biometric.mvi.reducer

import com.example.feature_authentication.presentation.biometric.mvi.model.BiometricsDomainState
import com.example.feature_authentication.presentation.biometric.mvi.model.BiometricsEvent
import com.example.feature_authentication.presentation.biometric.mvi.model.BiometricsSideEffect
import com.example.feature_authentication.presentation.biometric.mvi.model.BiometricsUiCommand
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class BiometricsReducer @Inject constructor(
    private val uiReducer: BiometricsUiReducer,
    private val domainReducer: BiometricsDomainReducer,
) : Reducer<BiometricsEvent, BiometricsDomainState, BiometricsSideEffect, BiometricsUiCommand> {

    override fun update(
        state: BiometricsDomainState,
        event: BiometricsEvent,
    ): Update<BiometricsDomainState, BiometricsSideEffect, BiometricsUiCommand> {
        return when (event) {
            is BiometricsEvent.None -> Update.nothing()
            is BiometricsEvent.Ui -> uiReducer.update(state, event)
            is BiometricsEvent.Domain -> domainReducer.update(state, event)
        }
    }

    fun getInitialState(): BiometricsDomainState {
        return BiometricsDomainState()
    }
}
