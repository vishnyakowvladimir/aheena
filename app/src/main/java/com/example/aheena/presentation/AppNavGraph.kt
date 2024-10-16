package com.example.aheena.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.aheena.navigation.FeatureComposableHolder
import com.example.aheena.presentation.screens.Test1

@Composable
internal fun AppNavGraph(
    navController: NavHostController,
    mediatorsHolder: FeatureComposableHolder,
) {

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = "test1",
        ) {
            composable("test1") {
                Test1()
            }
            mediatorsHolder.mediators.forEach { mediator ->
                mediator.featureComposable(navGraphBuilder = this)
            }
        }
    }
}

val LocalNavController = compositionLocalOf<NavHostController> {
    error("LocalNavController error")
}
