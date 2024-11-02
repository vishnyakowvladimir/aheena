package com.example.core.navigation.router

class FeatureRouterImp(
    private val navControllerHolder: NavControllerHolder,
    private val appRouter: NavRouter,
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
