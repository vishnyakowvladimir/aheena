package com.example.aheena.di.modules

import com.example.core_api.di.scope.ApplicationScope
import com.example.data_source_api.storage.authentication.AuthenticationStorage
import com.example.data_source_api.storage.biometrics.BiometricsStorage
import com.example.data_source_api.storage.theme.ThemeStorage
import com.example.data_source_api.storage.user_activity.UserActivityStorage
import com.example.data_source_impl.storage.authentication.AuthenticationStorageImpl
import com.example.data_source_impl.storage.biometrics.BiometricsStorageImpl
import com.example.data_source_impl.storage.theme.ThemeStorageImpl
import com.example.data_source_impl.storage.user_activity.UserActivityStorageImpl
import dagger.Binds
import dagger.Module

@Module
interface StorageModule {

    @Binds
    fun bindThemeStorage(source: ThemeStorageImpl): ThemeStorage

    @Binds
    fun bindAuthenticationStorage(source: AuthenticationStorageImpl): AuthenticationStorage

    @Binds
    fun bindBiometricsStorage(source: BiometricsStorageImpl): BiometricsStorage

    @ApplicationScope
    @Binds
    fun bindUserActivityStorage(source: UserActivityStorageImpl): UserActivityStorage
}
