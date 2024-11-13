package com.example.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.core.navigation.feature_destination.FeaturesDestination
import com.example.feature_splash_api.FeatureComposableSplash
import com.example.splash.presentation.Splash
import javax.inject.Inject

class FeatureComposableSplashImpl @Inject constructor() : FeatureComposableSplash {

    override fun featureComposable(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable<FeaturesDestination.SplashDestination> {
            Splash()
        }
    }
}
