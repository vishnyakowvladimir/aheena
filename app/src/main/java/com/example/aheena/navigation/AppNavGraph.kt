package com.example.aheena.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.core.navigation.feature_destination.SplashDestination

@Composable
internal fun AppNavGraph(
    navController: NavHostController,
    viewModelFactory: ViewModelProvider.Factory,
    composablesHolder: FeatureComposablesHolder,
) {
    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = SplashDestination(),
        ) {
            composablesHolder.composables.forEach { composable ->
                composable.featureComposable(navGraphBuilder = this)
            }
        }
    }
}

val LocalNavController = compositionLocalOf<NavHostController> {
    error("LocalNavController error")
}
