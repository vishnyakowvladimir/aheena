package com.example.aheena.navigation

import com.example.feature_authentication.navigation.FeatureComposableAuthentication
import com.example.feature_main.navigation.FeatureComposableMain
import com.example.splash.navigation.FeatureComposableSplash

class FeatureComposablesHolder {

    val composables = setOf(
        FeatureComposableAuthentication(),
        FeatureComposableSplash(),
        FeatureComposableMain()
    )
}
