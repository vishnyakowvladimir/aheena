package com.example.aheena.di.modules

import com.example.data_source_api.storage.authentication.AuthenticationStorage
import com.example.data_source_api.storage.biometrics.BiometricsStorage
import com.example.data_source_api.storage.theme.ThemeStorage
import com.example.data_source_impl.storage.authentication.AuthenticationStorageImpl
import com.example.data_source_impl.storage.biometrics.BiometricsStorageImpl
import com.example.data_source_impl.storage.theme.ThemeStorageImpl
import dagger.Binds
import dagger.Module

@Module
interface DataSourceModule {

    @Binds
    fun bindThemeSource(source: ThemeStorageImpl): ThemeStorage

    @Binds
    fun bindAuthenticationSource(source: AuthenticationStorageImpl): AuthenticationStorage

    @Binds
    fun bindBiometricsSource(source: BiometricsStorageImpl): BiometricsStorage
}
