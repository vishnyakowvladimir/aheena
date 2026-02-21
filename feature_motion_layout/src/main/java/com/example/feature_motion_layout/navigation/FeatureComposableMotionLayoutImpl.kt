package com.example.feature_motion_layout.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.core_api.navigation.feature_destination.FeaturesDestination
import com.example.feature_motion_layout.presentation.container.MotionLayoutContainer
import com.example.feature_motion_layout_api.FeatureComposableMotionLayout
import javax.inject.Inject

class FeatureComposableMotionLayoutImpl @Inject constructor() : FeatureComposableMotionLayout {

    override fun featureComposable(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable<FeaturesDestination.MotionLayoutDestination> {
            MotionLayoutContainer()
        }
    }
}
