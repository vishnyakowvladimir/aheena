package com.example.feature_tech.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.core_api.navigation.feature_destination.FeaturesDestination
import com.example.feature_tech_api.FeatureComposableTech
import javax.inject.Inject

class FeatureComposableTechImpl @Inject constructor() : FeatureComposableTech {

    override fun featureComposable(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable<FeaturesDestination.TechDestination> {}
    }
}
