package com.example.core_impl.navigation

import com.example.core.holder.ActivityHolder
import com.example.core.navigation.router.AbstractNavRouter
import com.example.core.navigation.router.NavControllerHolder

class AppRouterImpl(
    private val navControllerHolder: NavControllerHolder,
    private val activityHolder: ActivityHolder,
) : AbstractNavRouter(navControllerHolder) {

    override fun popBackStack(): Boolean {
        val navController = navControllerHolder.navHostController

        val isPopUp = navController?.popBackStack() ?: false
        return if (isPopUp) {
            true
        } else {
            activityHolder.activity?.finish()
            activityHolder.activity != null
        }
    }
}
