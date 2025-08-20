package com.example.core_impl.navigation

import com.example.core_api.holder.ActivityHolder
import com.example.core_api.navigation.router.AbstractNavRouter
import com.example.core_api.navigation.router.NavControllerHolder
import javax.inject.Inject

class AppRouterImpl @Inject constructor(
    private val navControllerHolder: NavControllerHolder,
    private val activityHolder: ActivityHolder,
) : AbstractNavRouter(navControllerHolder) {

    override fun popBackStack(): Boolean {
        val navController = navControllerHolder.navHostController

        val isPopUp = navController?.popBackStack() ?: false
        return if (isPopUp) {
            true
        } else {
            /**
             * finishAndRemoveTask() - закрывает активити и удаляет из списка задач
             */
            activityHolder.activity?.finishAndRemoveTask()
            activityHolder.activity != null
        }
    }
}
