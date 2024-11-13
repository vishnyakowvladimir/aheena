package com.example.feature_itunes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.core.navigation.feature_destination.FeaturesDestination
import com.example.feature_itunes.presentation.container.ItunesContainer
import com.example.feature_itunes_api.FeatureComposableItunes
import javax.inject.Inject

class FeatureComposableItunesImpl @Inject constructor() : FeatureComposableItunes {

    override fun featureComposable(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable<FeaturesDestination.ItunesDestination> {
            ItunesContainer()
        }
    }
}
