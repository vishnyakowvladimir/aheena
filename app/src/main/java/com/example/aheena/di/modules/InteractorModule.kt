package com.example.aheena.di.modules

import com.example.data_sdk_api.interactor.ThemeInteractor
import com.example.data_sdk_impl.interactor.ThemeInteractorImpl
import dagger.Binds
import dagger.Module

@Module
interface InteractorModule {

    @Binds
    fun bindThemeInteractor(repository: ThemeInteractorImpl): ThemeInteractor
}
