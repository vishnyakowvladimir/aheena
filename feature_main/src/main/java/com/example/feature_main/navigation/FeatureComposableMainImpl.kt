package com.example.feature_main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.core.navigation.feature_destination.FeaturesDestination
import com.example.feature_main.presentation.container.MainContainer
import com.example.feature_main_api.FeatureComposableMain
import javax.inject.Inject

class FeatureComposableMainImpl @Inject constructor() : FeatureComposableMain {

    override fun featureComposable(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable<FeaturesDestination.MainDestination> {
            MainContainer()
        }
    }
}
