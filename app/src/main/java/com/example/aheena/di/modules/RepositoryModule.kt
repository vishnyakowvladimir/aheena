package com.example.aheena.di.modules

import com.example.data_source_api.repository.authentication.AuthenticationRepository
import com.example.data_source_api.repository.biometrics.BiometricsRepository
import com.example.data_source_api.repository.itunes.ItunesRepository
import com.example.data_source_api.repository.theme.ThemeRepository
import com.example.data_source_api.repository.user_activity.UserActivityRepository
import com.example.data_source_impl.repository.authentication.AuthenticationRepositoryImpl
import com.example.data_source_impl.repository.biometrics.BiometricsRepositoryImpl
import com.example.data_source_impl.repository.itunes.ItunesRepositoryImpl
import com.example.data_source_impl.repository.theme.ThemeRepositoryImpl
import com.example.data_source_impl.repository.user_activity.UserActivityRepositoryImpl
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

    @Binds
    fun bindUserActivityRepository(repository: UserActivityRepositoryImpl): UserActivityRepository

    @Binds
    fun bindItunesRepository(repository: ItunesRepositoryImpl): ItunesRepository
}
