package com.example.aheena.di.modules

import com.example.data_source_api.source.authentication.AuthenticationSource
import com.example.data_source_api.source.theme.ThemeSource
import com.example.data_source_impl.source.authentication.AuthenticationSourceImpl
import com.example.data_source_impl.source.theme.ThemeSourceImpl
import dagger.Binds
import dagger.Module

@Module
interface DataSourceModule {

    @Binds
    fun bindThemeSource(source: ThemeSourceImpl): ThemeSource

    @Binds
    fun bindAuthenticationSource(source: AuthenticationSourceImpl): AuthenticationSource
}
