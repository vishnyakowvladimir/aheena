package com.example.aheena.di.modules

import com.example.data_source_api.repository.theme.ThemeRepository
import com.example.data_source_impl.repository.theme.ThemeRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindThemeRepository(repository: ThemeRepositoryImpl): ThemeRepository
}
