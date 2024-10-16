package com.example.aheena.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.aheena.presentation.screens.ProfileScreen
import com.example.aheena.presentation.screens.Test1
import com.example.aheena.test.Profile

@Composable
internal fun AppNavGraph(navController: NavHostController) {

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = "test1",
        ) {
            composable("test1") {
                Test1()
            }
            composable<Profile>() { backStackEntry ->
                val profile = backStackEntry.toRoute<Profile>()
                ProfileScreen(profile)
            }
        }
    }
}

val LocalNavController = compositionLocalOf<NavHostController> {
    error("LocalNavController error")
}
