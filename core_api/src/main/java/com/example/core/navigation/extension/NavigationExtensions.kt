package com.example.core.navigation.extension

import androidx.navigation.NavHostController
import com.example.core.navigation.base.BaseDestination

fun NavHostController.replace(destination: BaseDestination) {
    navigate(destination) {
        popUpTo(currentBackStackEntry?.destination?.route ?: return@navigate) {
            inclusive = true
        }
    }
}

fun NavHostController.replaceAll(destination: BaseDestination) {
    navigate(destination) {
        popUpTo(0)
    }
}
