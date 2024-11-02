package com.example.feature_authentication.di

import androidx.lifecycle.ViewModelProvider
import com.example.core.navigation.router.FeatureRouter

interface AuthenticationDependenciesProvider {
    fun provideFeatureRouter(): FeatureRouter
    fun provideViewModelFactory(): ViewModelProvider.Factory
}
