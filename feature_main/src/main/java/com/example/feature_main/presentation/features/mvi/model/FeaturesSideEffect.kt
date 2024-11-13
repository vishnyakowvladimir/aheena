package com.example.feature_main.presentation.features.mvi.model

import com.example.core_api.navigation.feature_destination.FeaturesDestination

internal sealed interface FeaturesSideEffect {

    sealed interface Ui : FeaturesSideEffect {
        data class OpenFeature(val destination: FeaturesDestination) : Ui
    }

    sealed interface Domain : FeaturesSideEffect
}


