package com.example.core_impl.presentation.router

import com.example.core.navigation.base.BaseDestination
import com.example.core.navigation.router.AppRouter
import com.example.core_impl.holder.NavControllerHolder
import javax.inject.Inject

class AppRouterImpl @Inject constructor(
    private val navControllerHolder: NavControllerHolder,
): AppRouter {

    override fun navigate(destination: BaseDestination): Boolean {
        val navController = navControllerHolder.navController
        navController?.navigate(destination)
        return navController != null
    }

    override fun popBackStack(): Boolean {
        val navController = navControllerHolder.navController
        navController?.popBackStack()
        return navController != null
    }
}
