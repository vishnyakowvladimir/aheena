package com.example.feature_authentication.di

import androidx.lifecycle.ViewModelProvider
import com.example.core.navigation.router.NavControllerHolder

interface AuthenticationDependenciesProvider {
    fun provideNavControllerHolder(): NavControllerHolder
    fun provideViewModelFactory(): ViewModelProvider.Factory
}
