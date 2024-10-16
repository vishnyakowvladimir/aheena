package com.example.feature_authorization.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.core.navigation.base.FeatureComposable
import com.example.core.navigation.feature_destination.AuthenticationDestination
import com.example.feature_authorization.presentation.AuthenticationContainer

class AuthenticationComposable : FeatureComposable {

    override fun featureComposable(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.composable<AuthenticationDestination> {
            AuthenticationContainer()
        }
    }
}
