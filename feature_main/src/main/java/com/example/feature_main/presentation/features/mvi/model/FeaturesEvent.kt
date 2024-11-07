package com.example.feature_main.presentation.features.mvi.model

import com.example.feature_main.presentation.features.model.FeatureScreen

internal sealed interface FeaturesEvent {

    data object None : FeaturesEvent

    sealed interface Ui : FeaturesEvent {
        data class OnItemClick(val featureScreen: FeatureScreen) : Ui
    }

    sealed interface Domain : FeaturesEvent
}
