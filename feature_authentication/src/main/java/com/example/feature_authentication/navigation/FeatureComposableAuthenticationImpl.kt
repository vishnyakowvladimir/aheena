package com.example.feature_authentication.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.core_api.navigation.feature_destination.FeaturesDestination
import com.example.feature_authentication.presentation.container.AuthenticationContainer
import com.example.feature_authentication_api.FeatureComposableAuthentication
import javax.inject.Inject

class FeatureComposableAuthenticationImpl @Inject constructor() : FeatureComposableAuthentication {

    override fun featureComposable(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable<FeaturesDestination.AuthenticationDestination> {
            AuthenticationContainer()
        }
    }
}
