package com.example.feature_itunes.presentation.container

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.core_api.di.extension.clearComponent
import com.example.core_api.di.extension.getComponent
import com.example.feature_itunes.di.ItunesComponent
import com.example.feature_itunes.navigation.LocalDestinationItunes
import com.example.feature_itunes.presentation.itunes.ItunesScreen
import com.example.lib_ui.utils.ComposableLifecycle

@Composable
internal fun ItunesContainer() {
    val context = LocalContext.current
    val component = context.getComponent<ItunesComponent>()

    val navControllerHolder = component.provideNavControllerHolder()
    val viewModelFactory = component.provideViewModelFactory()
    val viewModel = viewModel<ItunesContainerViewModel>(factory = viewModelFactory)

    val navController = rememberNavController()
    navControllerHolder.navHostController = navController

    // TODO работает некорректно
    // отписка происходит быстрее, чем вызывается onDestroy
    // поэтому onDestroy не вызывается
    ComposableLifecycle(
        onDestroy = {
            context.clearComponent<ItunesComponent>()
        }
    )

    NavHost(
        navController = navController,
        startDestination = LocalDestinationItunes.Itunes,
    ) {
        composable<LocalDestinationItunes.Itunes> {
            ItunesScreen(viewModel = viewModel(factory = viewModelFactory))
        }
    }
}
