package com.example.feature_main.di

import androidx.lifecycle.ViewModelProvider
import com.example.core_api.navigation.router.NavControllerHolder

interface MainDependenciesProvider {
    fun provideNavControllerHolder(): NavControllerHolder
    fun provideViewModelFactory(): ViewModelProvider.Factory
}
