package com.example.core_api.navigation.router

import com.example.core_api.di.qualifier.MainRouter
import javax.inject.Inject

class FeatureRouterImp @Inject constructor(
    private val navControllerHolder: NavControllerHolder,
    @MainRouter private val appRouter: NavRouter,
) : AbstractNavRouter(navControllerHolder) {

    override fun popBackStack(): Boolean {
        val navController = navControllerHolder.navHostController

        val isPopUp = navController?.popBackStack() ?: false
        return if (isPopUp) {
            true
        } else {
            appRouter.popBackStack()
        }
    }
}
