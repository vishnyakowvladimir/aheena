package com.example.feature_itunes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.core.navigation.base.FeatureComposable
import com.example.core.navigation.feature_destination.FeaturesDestination
import com.example.feature_itunes.presentation.container.ItunesContainer

class FeatureComposableItunes : FeatureComposable {

    override fun featureComposable(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable<FeaturesDestination.ItunesDestination> {
            ItunesContainer()
        }
    }
}
