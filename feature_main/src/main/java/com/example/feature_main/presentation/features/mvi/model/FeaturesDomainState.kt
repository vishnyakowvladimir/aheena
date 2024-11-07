package com.example.feature_main.presentation.features.mvi.model

import com.example.feature_main.presentation.features.model.FeatureScreen

internal data class FeaturesDomainState(
    val featuresScreens: List<FeatureScreen> = FeatureScreen.entries,
)
