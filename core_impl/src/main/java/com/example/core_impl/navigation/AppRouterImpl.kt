package com.example.core_impl.navigation

import androidx.navigation.NavHostController
import com.example.core.holder.ActivityHolder
import com.example.core.navigation.base.BaseDestination
import com.example.core.navigation.router.AppRouter
import javax.inject.Inject

class AppRouterImpl @Inject constructor(
    private val navController: NavHostController,
    private val activityHolder: ActivityHolder,
) : AppRouter {

    override fun navigate(destination: BaseDestination): Boolean {
        navController.navigate(destination) {
            launchSingleTop = true
        }
        return true
    }

    override fun replace(destination: BaseDestination): Boolean {
        navController.navigate(destination) {
            popUpTo(navController.currentBackStackEntry?.destination?.route ?: return@navigate) {
                inclusive = true
            }
        }
        return true
    }

    override fun replaceAll(destination: BaseDestination): Boolean {
        navController.navigate(destination) {
            popUpTo(0)
        }
        return true
    }

    override fun popBackStack(): Boolean {
        val isPopUp = navController.popBackStack() ?: false
        return if (isPopUp) {
            true
        } else {
            activityHolder.activity?.finish()
            activityHolder.activity != null
        }
    }
}
