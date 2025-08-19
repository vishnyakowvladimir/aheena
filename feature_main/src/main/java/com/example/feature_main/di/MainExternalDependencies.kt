package com.example.feature_main.di

import com.example.core_api.controller.session.UserSessionController
import com.example.core_api.controller.theme.ThemeManager
import com.example.core_api.di.qualifier.MainRouter
import com.example.core_api.navigation.router.NavRouter
import com.example.core_api.pending_navigation.PendingNavigationManager
import com.example.core_api.utils.string_provider.StringProvider
import javax.inject.Inject

class MainExternalDependencies @Inject constructor(
    @get:MainRouter @MainRouter val mainRouter: NavRouter,
    val stringProvider: StringProvider,
    val userSessionController: UserSessionController,
    val themeManager: ThemeManager,
    val pendingNavigationManager: PendingNavigationManager,
)
