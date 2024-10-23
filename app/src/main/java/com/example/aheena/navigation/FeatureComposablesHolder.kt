package com.example.aheena.navigation

import com.example.core.navigation.base.FeatureComposable
import com.example.feature_authentication.navigation.AuthenticationComposable
import com.example.splash.navigation.SplashComposable

class FeatureComposablesHolder {

    val composables = setOf<FeatureComposable>(
        AuthenticationComposable(),
        SplashComposable(),
    )
}
