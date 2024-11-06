package com.example.feature_main.presentation.features.mvi.model

internal sealed interface FeaturesEvent {

    sealed interface None : FeaturesEvent

    sealed interface Ui : FeaturesEvent

    sealed interface Domain : FeaturesEvent
}
