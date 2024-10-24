package com.example.aheena.di.dependencies

import com.example.feature_authentication.di.AuthenticationExternalDependencies

interface FeaturesDependenciesProvider {
    fun authenticationDependencies(): AuthenticationExternalDependencies
}
