package com.example.aheena.presentation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.aheena.navigation.FeatureComposablesHolder
import com.example.core.navigation.feature_destination.SplashDestination
import com.example.core_impl.navigation.NavControllerHolder

@Composable
internal fun AppNavGraph(
    navControllerHolder: NavControllerHolder,
    viewModelFactory: ViewModelProvider.Factory,
    composablesHolder: FeatureComposablesHolder,
) {

    val navController  = rememberNavController()
    navControllerHolder.navController = navController

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
