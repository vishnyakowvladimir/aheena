package com.example.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.core.navigation.base.FeatureComposable
import com.example.core.navigation.feature_destination.FeaturesDestination
import com.example.splash.presentation.Splash

class FeatureComposableSplash : FeatureComposable {

    override fun featureComposable(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable<FeaturesDestination.SplashDestination> {
            Splash()
        }
    }
}
