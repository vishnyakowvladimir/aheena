package com.example.feature_itunes.di

import androidx.lifecycle.ViewModelProvider
import com.example.core.navigation.router.NavControllerHolder

interface ItunesDependenciesProvider {
    fun provideNavControllerHolder(): NavControllerHolder
    fun provideViewModelFactory(): ViewModelProvider.Factory
}
