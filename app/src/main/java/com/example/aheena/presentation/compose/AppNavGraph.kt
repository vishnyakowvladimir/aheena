package com.example.aheena.presentation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.aheena.navigation.FeatureComposablesHolder
import com.example.core.navigation.feature_destination.AuthenticationDestination

@Composable
internal fun AppNavGraph(
    navController: NavHostController,
    viewModelFactory: ViewModelProvider.Factory,
    composablesHolder: FeatureComposablesHolder,
) {

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = AuthenticationDestination(),
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
