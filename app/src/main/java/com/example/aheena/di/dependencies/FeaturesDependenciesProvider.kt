package com.example.aheena.di.dependencies

import com.example.feature_authentication.di.AuthenticationExternalDependencies
import com.example.feature_itunes.di.ItunesExternalDependencies
import com.example.feature_main.di.MainExternalDependencies
import com.example.feature_tech.di.TechExternalDependencies

interface FeaturesDependenciesProvider {
    fun authenticationDependencies(): AuthenticationExternalDependencies
    fun mainDependencies(): MainExternalDependencies
    fun itunesDependencies(): ItunesExternalDependencies
    fun techDependencies(): TechExternalDependencies
}
