package com.example.core.navigation.router

import com.example.core.navigation.base.BaseDestination

abstract class AbstractNavRouter(
    private val navControllerHolder: NavControllerHolder,
) : NavRouter {

    override fun navigate(destination: BaseDestination): Boolean {
        val navController = navControllerHolder.navHostController

        navController?.navigate(destination) {
            launchSingleTop = true
        }
        return navController != null
    }

    override fun replace(destination: BaseDestination): Boolean {
        val navController = navControllerHolder.navHostController

        navController?.navigate(destination) {
            popUpTo(navController.currentBackStackEntry?.destination?.route ?: return@navigate) {
                inclusive = true
            }
        }
        return navController != null
    }

    override fun replaceAll(destination: BaseDestination): Boolean {
        val navController = navControllerHolder.navHostController

        navController?.navigate(destination) {
            popUpTo(0)
        }
        return navController != null
    }

    override fun popBackStack(): Boolean {
        val navController = navControllerHolder.navHostController
        return navController?.popBackStack() ?: false
    }
}
