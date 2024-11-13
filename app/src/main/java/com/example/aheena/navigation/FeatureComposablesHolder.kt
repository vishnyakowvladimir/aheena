package com.example.aheena.navigation

import com.example.feature_authentication_api.FeatureComposableAuthentication
import com.example.feature_itunes.navigation.FeatureComposableItunes
import com.example.feature_main.navigation.FeatureComposableMain
import com.example.feature_splash_api.FeatureComposableSplash
import javax.inject.Inject

class FeatureComposablesHolder @Inject constructor(
    featureComposableAuthenticationImpl: FeatureComposableAuthentication,
    featureComposableSplash: FeatureComposableSplash,
) {

    val composables = setOf(
        featureComposableAuthenticationImpl,
        featureComposableSplash,
        FeatureComposableMain(),
        FeatureComposableItunes(),
    )
}
