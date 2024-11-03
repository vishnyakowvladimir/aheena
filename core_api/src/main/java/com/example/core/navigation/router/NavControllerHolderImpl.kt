package com.example.core.navigation.router

import androidx.navigation.NavHostController
import javax.inject.Inject

class NavControllerHolderImpl @Inject constructor() : NavControllerHolder {
    override var navHostController: NavHostController? = null
}
