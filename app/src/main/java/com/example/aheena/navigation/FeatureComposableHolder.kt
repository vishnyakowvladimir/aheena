package com.example.aheena.navigation

import com.example.core.navigation.base.FeatureComposable
import com.example.feature_authorization.navigation.AuthenticationComposable

class FeatureComposableHolder {

    val mediators = setOf<FeatureComposable>(
        AuthenticationComposable()
    )
}
