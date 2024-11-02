package com.example.feature_main.container

import com.example.core.controller.session.UserSessionController
import com.example.core.di.qualifier.MainRouter
import com.example.core.navigation.router.NavRouter
import com.example.core.presentation.base.BaseViewModel
import javax.inject.Inject

class MainContainerViewModel @Inject constructor(
    @MainRouter private val mainRouter: NavRouter,
    private val router: NavRouter,
    userSessionController: UserSessionController,
) : BaseViewModel() {

    init {
        userSessionController.enable()
    }

    fun onBackPressed() {
        router.popBackStack()
    }
}
