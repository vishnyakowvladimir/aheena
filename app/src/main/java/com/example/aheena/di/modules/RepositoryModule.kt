package com.example.aheena.di.modules

import com.example.data_source_api.repository.authentication.AuthenticationRepository
import com.example.data_source_api.repository.biometrics.BiometricsRepository
import com.example.data_source_api.repository.theme.ThemeRepository
import com.example.data_source_impl.repository.authentication.AuthenticationRepositoryImpl
import com.example.data_source_impl.repository.biometrics.BiometricsRepositoryImpl
import com.example.data_source_impl.repository.theme.ThemeRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindThemeRepository(repository: ThemeRepositoryImpl): ThemeRepository

    @Binds
    fun bindAuthenticationRepository(repository: AuthenticationRepositoryImpl): AuthenticationRepository

    @Binds
    fun bindBiometricsRepository(repository: BiometricsRepositoryImpl): BiometricsRepository
}
