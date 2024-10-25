package com.example.core_impl.navigation

import com.example.core.navigation.base.BaseDestination
import com.example.core.navigation.router.AppRouter
import com.example.core_impl.holder.ActivityHolder
import com.example.core_impl.holder.NavControllerHolder
import javax.inject.Inject

class AppRouterImpl @Inject constructor(
    private val navControllerHolder: NavControllerHolder,
    private val activityHolder: ActivityHolder,
) : AppRouter {

    override fun navigate(destination: BaseDestination): Boolean {
        val navController = navControllerHolder.navController
        navController?.navigate(destination) {
            launchSingleTop = true
        }
        return navController != null
    }

    override fun replace(destination: BaseDestination): Boolean {
        val navController = navControllerHolder.navController
        navController?.navigate(destination) {
            popUpTo(navController.currentBackStackEntry?.destination?.route ?: return@navigate) {
                inclusive = true
            }
        }
        return navController != null
    }

    override fun replaceAll(destination: BaseDestination): Boolean {
        val navController = navControllerHolder.navController
        navController?.navigate(destination) {
            popUpTo(0)
        }
        return navController != null
    }

    override fun popBackStack(): Boolean {
        val navController = navControllerHolder.navController

        val isPopUp = navController?.popBackStack() ?: false
        return if (isPopUp) {
            true
        } else {
            activityHolder.activity?.finish()
            activityHolder.activity != null
        }
    }
}
