package com.example.feature_main.di

import android.content.Context
import com.example.core.controller.session.UserSessionController
import com.example.core.di.qualifier.MainRouter
import com.example.core.holder.ActivityHolder
import com.example.core.navigation.router.NavRouter
import com.example.core.utils.string_provider.StringProvider
import javax.inject.Inject

class MainExternalDependencies @Inject constructor(
    val applicationContext: Context,
    val activityHolder: ActivityHolder,
    @get:MainRouter @MainRouter val mainRouter: NavRouter,
    val stringProvider: StringProvider,
    val userSessionController: UserSessionController,
)
