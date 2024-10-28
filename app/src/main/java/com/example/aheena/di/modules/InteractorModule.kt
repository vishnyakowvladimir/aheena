package com.example.aheena.di.modules

import com.example.data_sdk_api.interactor.authentication.AuthenticationInteractor
import com.example.data_sdk_api.interactor.theme.ThemeInteractor
import com.example.data_sdk_impl.interactor.authentication.AuthenticationInteractorImpl
import com.example.data_sdk_impl.interactor.theme.ThemeInteractorImpl
import dagger.Binds
import dagger.Module

@Module
interface InteractorModule {

    @Binds
    fun bindThemeInteractor(interactor: ThemeInteractorImpl): ThemeInteractor

    @Binds
    fun bindAuthenticationInteractor(interactor: AuthenticationInteractorImpl): AuthenticationInteractor
}
