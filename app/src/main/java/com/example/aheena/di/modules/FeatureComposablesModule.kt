package com.example.aheena.di.modules

import com.example.feature_authentication.navigation.FeatureComposableAuthenticationImpl
import com.example.feature_authentication_api.FeatureComposableAuthentication
import dagger.Binds
import dagger.Module

@Module
internal interface FeatureComposablesModule {

    @Binds
    fun bindAuthentication(composable: FeatureComposableAuthenticationImpl): FeatureComposableAuthentication
}
