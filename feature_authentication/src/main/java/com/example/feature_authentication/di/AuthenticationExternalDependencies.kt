package com.example.feature_authentication.di

import android.content.Context
import com.example.core.navigation.router.AppRouter
import com.example.core.utils.string_provider.StringProvider
import com.example.data_sdk_api.interactor.authentication.AuthenticationInteractor
import com.example.data_sdk_api.interactor.biometrics.BiometricsInteractor
import javax.inject.Inject

class AuthenticationExternalDependencies @Inject constructor(
    val context: Context,
    val router: AppRouter,
    val stringProvider: StringProvider,
    val authenticationInteractor: AuthenticationInteractor,
    val biometricsInteractor: BiometricsInteractor,
)
