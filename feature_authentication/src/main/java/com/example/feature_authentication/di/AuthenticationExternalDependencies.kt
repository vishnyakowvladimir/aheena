package com.example.feature_authentication.di

import android.content.Context
import com.example.core.controller.logout.LogoutController
import com.example.core.di.qualifier.MainRouter
import com.example.core.holder.ActivityHolder
import com.example.core.navigation.router.NavRouter
import com.example.core.utils.string_provider.StringProvider
import com.example.data_sdk_api.interactor.authentication.AuthenticationInteractor
import com.example.data_sdk_api.interactor.biometrics.BiometricsInteractor
import javax.inject.Inject

class AuthenticationExternalDependencies @Inject constructor(
    val applicationContext: Context,
    val activityHolder: ActivityHolder,
    @get:MainRouter @MainRouter val mainRouter2: NavRouter,
    val stringProvider: StringProvider,
    val authenticationInteractor: AuthenticationInteractor,
    val biometricsInteractor: BiometricsInteractor,
    val logoutController: LogoutController,
)
