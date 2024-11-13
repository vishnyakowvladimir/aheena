package com.example.feature_tech.di

import androidx.lifecycle.ViewModelProvider
import com.example.core_api.navigation.router.NavControllerHolder

interface TechDependenciesProvider {
    fun provideNavControllerHolder(): NavControllerHolder
    fun provideViewModelFactory(): ViewModelProvider.Factory
}
