package com.example.core.navigation.router

import androidx.navigation.NavHostController
import com.example.core.navigation.base.BaseDestination

class FeatureRouter {

    private var navController: NavHostController? = null

    fun setNavController(navController: NavHostController) {
        this.navController = navController
    }


    fun navigate(destination: BaseDestination): Boolean {
        navController?.navigate(destination) {
            launchSingleTop = true
        }
        return true
    }

    fun replace(destination: BaseDestination): Boolean {
        navController?.navigate(destination) {
            popUpTo(navController?.currentBackStackEntry?.destination?.route ?: return@navigate) {
                inclusive = true
            }
        }
        return true
    }

    fun replaceAll(destination: BaseDestination): Boolean {
        navController?.navigate(destination) {
            popUpTo(0)
        }
        return true
    }

    fun popBackStack(): Boolean {
        val isPopUp = navController?.popBackStack() ?: false
        return true
    }
}
