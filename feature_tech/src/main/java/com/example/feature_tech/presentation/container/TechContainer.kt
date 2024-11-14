package com.example.feature_tech.presentation.container

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.core_api.di.extension.clearComponent
import com.example.core_api.di.extension.getComponent
import com.example.feature_tech.di.TechComponent
import com.example.feature_tech.navigation.LocalDestinationTech
import com.example.feature_tech.presentation.select_url.SelectUrlScreen
import com.example.lib_ui.utils.ComposableLifecycle

@Composable
internal fun TechContainer() {
    val context = LocalContext.current
    val component = context.getComponent<TechComponent>()

    val navControllerHolder = component.provideNavControllerHolder()
    val viewModelFactory = component.provideViewModelFactory()
    val viewModel = viewModel<TechContainerViewModel>(factory = viewModelFactory)

    val navController = rememberNavController()
    navControllerHolder.navHostController = navController

    ComposableLifecycle(
        onDestroy = {
            context.clearComponent<TechComponent>()
        }
    )

    NavHost(
        navController = navController,
        startDestination = LocalDestinationTech.SelectUrl,
    ) {
        composable<LocalDestinationTech.SelectUrl> {
            SelectUrlScreen(viewModel = viewModel(factory = viewModelFactory))
        }
    }
}
