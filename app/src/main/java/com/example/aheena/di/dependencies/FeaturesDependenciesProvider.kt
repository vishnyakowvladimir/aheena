package com.example.aheena.di.dependencies

import com.example.feature_authentication.di.AuthenticationExternalDependencies
import com.example.feature_main.di.MainExternalDependencies

interface FeaturesDependenciesProvider {
    fun authenticationDependencies(): AuthenticationExternalDependencies
    fun mainDependencies(): MainExternalDependencies
}
