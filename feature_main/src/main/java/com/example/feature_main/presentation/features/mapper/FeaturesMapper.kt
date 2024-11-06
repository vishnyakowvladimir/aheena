package com.example.feature_main.presentation.features.mapper

import com.example.feature_main.presentation.features.model.FeaturesUiState
import com.example.feature_main.presentation.features.mvi.model.FeaturesDomainState
import javax.inject.Inject

internal class FeaturesMapper @Inject constructor() {
    fun map(domainState: FeaturesDomainState): FeaturesUiState {
        return FeaturesUiState(
            isLoading = domainState.isLoading,
        )
    }
}
