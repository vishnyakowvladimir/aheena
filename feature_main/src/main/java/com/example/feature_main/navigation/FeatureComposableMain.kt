package com.example.feature_main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.core.navigation.base.FeatureComposable
import com.example.core.navigation.feature_destination.MainDestination
import com.example.feature_main.container.MainContainer

class FeatureComposableMain : FeatureComposable {

    override fun featureComposable(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable<MainDestination> {
            MainContainer()
        }
    }
}
