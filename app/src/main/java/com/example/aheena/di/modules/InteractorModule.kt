package com.example.aheena.di.modules

import com.example.data_sdk_api.interactor.authentication.AuthenticationInteractor
import com.example.data_sdk_api.interactor.biometrics.BiometricsInteractor
import com.example.data_sdk_api.interactor.theme.ThemeInteractor
import com.example.data_sdk_api.interactor.user_activity.UserActivityInteractor
import com.example.data_sdk_impl.interactor.authentication.AuthenticationInteractorImpl
import com.example.data_sdk_impl.interactor.biometrics.BiometricsInteractorImpl
import com.example.data_sdk_impl.interactor.theme.ThemeInteractorImpl
import com.example.data_sdk_impl.interactor.user_activity.UserActivityInteractorImpl
import dagger.Binds
import dagger.Module

@Module
interface InteractorModule {

    @Binds
    fun bindThemeInteractor(interactor: ThemeInteractorImpl): ThemeInteractor

    @Binds
    fun bindAuthenticationInteractor(interactor: AuthenticationInteractorImpl): AuthenticationInteractor

    @Binds
    fun bindBiometricsInteractor(interactor: BiometricsInteractorImpl): BiometricsInteractor

    @Binds
    fun bindUserActivityInteractor(interactor: UserActivityInteractorImpl): UserActivityInteractor
}
