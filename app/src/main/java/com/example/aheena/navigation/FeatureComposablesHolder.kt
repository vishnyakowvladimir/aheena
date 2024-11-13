package com.example.aheena.navigation

import com.example.feature_authentication_api.FeatureComposableAuthentication
import com.example.feature_itunes_api.FeatureComposableItunes
import com.example.feature_main_api.FeatureComposableMain
import com.example.feature_splash_api.FeatureComposableSplash
import javax.inject.Inject

class FeatureComposablesHolder @Inject constructor(
    featureComposableAuthenticationImpl: FeatureComposableAuthentication,
    featureComposableSplash: FeatureComposableSplash,
    featureComposableMain: FeatureComposableMain,
    featureComposableItunes: FeatureComposableItunes,
) {

    val composables = setOf(
        featureComposableAuthenticationImpl,
        featureComposableSplash,
        featureComposableMain,
        featureComposableItunes,
    )
}
