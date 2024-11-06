package com.example.feature_main.presentation.features.mvi.model

internal sealed interface FeaturesSideEffect {

    sealed interface Ui : FeaturesSideEffect

    sealed interface Domain : FeaturesSideEffect
}


