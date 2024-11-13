package com.example.aheena.di.modules

import com.example.feature_authentication.navigation.FeatureComposableAuthenticationImpl
import com.example.feature_authentication_api.FeatureComposableAuthentication
import com.example.feature_main.navigation.FeatureComposableMainImpl
import com.example.feature_main_api.FeatureComposableMain
import com.example.feature_splash_api.FeatureComposableSplash
import com.example.splash.navigation.FeatureComposableSplashImpl
import dagger.Binds
import dagger.Module

@Module
internal interface FeatureComposablesModule {

    @Binds
    fun bindAuthentication(composable: FeatureComposableAuthenticationImpl): FeatureComposableAuthentication

    @Binds
    fun bindSplash(composable: FeatureComposableSplashImpl): FeatureComposableSplash

    @Binds
    fun bindMain(composable: FeatureComposableMainImpl): FeatureComposableMain
}
