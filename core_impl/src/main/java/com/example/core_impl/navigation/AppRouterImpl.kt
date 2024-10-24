package com.example.core_impl.navigation

import android.annotation.SuppressLint
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
        navController?.navigate(destination)
        return navController != null
    }

    override fun replace(destination: BaseDestination): Boolean {
        val navController = navControllerHolder.navController
        navController?.navigate(destination) {
            navController.popBackStack()
        }
        return navController != null
    }

    @SuppressLint("RestrictedApi")
    override fun popBackStack(): Boolean {
        val navController = navControllerHolder.navController

        navController?.currentBackStack?.value?.size

        return if ((navController?.currentBackStack?.value?.size ?: 0) <= 2) {
            activityHolder.activity?.finish()
            activityHolder.activity != null
        } else {
            navController?.popBackStack()
            true
        }
    }
}
