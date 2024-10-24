package com.example.feature_authentication.di

import androidx.navigation.NavHostController

interface AuthenticationDependenciesProvider {
    fun provideNavController(): NavHostController
}
