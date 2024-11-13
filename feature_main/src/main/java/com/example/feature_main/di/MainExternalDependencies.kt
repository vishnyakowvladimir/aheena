package com.example.feature_main.di

import android.content.Context
import com.example.core_api.controller.logout.LogoutController
import com.example.core_api.controller.session.UserSessionController
import com.example.core_api.controller.theme.ThemeManager
import com.example.core_api.di.qualifier.MainRouter
import com.example.core_api.holder.ActivityHolder
import com.example.core_api.navigation.router.NavRouter
import com.example.core_api.utils.string_provider.StringProvider
import javax.inject.Inject

class MainExternalDependencies @Inject constructor(
    val applicationContext: Context,
    val activityHolder: ActivityHolder,
    @get:MainRouter @MainRouter val mainRouter: NavRouter,
    val stringProvider: StringProvider,
    val userSessionController: UserSessionController,
    val logoutController: LogoutController,
    val themeManager: ThemeManager,
)
