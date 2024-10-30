package com.example.feature_authentication.presentation.biometric.mapper

import com.example.feature_authentication.presentation.biometric.model.BiometricsUiState
import com.example.feature_authentication.presentation.biometric.mvi.model.BiometricsDomainState
import javax.inject.Inject

internal class BiometricsMapper @Inject constructor() {

    fun map(domainState: BiometricsDomainState): BiometricsUiState {
        return BiometricsUiState(
            isLoading = domainState.isLoading,
        )
    }
}
