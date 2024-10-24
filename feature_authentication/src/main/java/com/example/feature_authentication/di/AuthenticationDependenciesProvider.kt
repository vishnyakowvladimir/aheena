package com.example.feature_authentication.di

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController

interface AuthenticationDependenciesProvider {
    fun provideNavController(): NavHostController
    fun provideViewModelFactory(): ViewModelProvider.Factory
}
