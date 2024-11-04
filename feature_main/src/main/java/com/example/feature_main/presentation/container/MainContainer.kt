package com.example.feature_main.presentation.container

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.core.di.extension.clearComponent
import com.example.core.di.extension.getComponent
import com.example.feature_main.di.MainComponent
import com.example.feature_main.navigation.LocalDestinationMain
import com.example.feature_main.presentation.main.MainScreen
import com.example.lib_ui.utils.ComposableLifecycle

@Composable
internal fun MainContainer() {
    val context = LocalContext.current
    val component = context.getComponent<MainComponent>()

    val navControllerHolder = component.provideNavControllerHolder()
    val viewModelFactory = component.provideViewModelFactory()
    val viewModel = viewModel<MainContainerViewModel>(factory = viewModelFactory)

    val navController = rememberNavController()
    navControllerHolder.navHostController = navController

    ComposableLifecycle(
        onDestroy = {
            context.clearComponent<MainComponent>()
        }
    )

    NavHost(
        navController = navController,
        startDestination = LocalDestinationMain.Main,
    ) {

        composable<LocalDestinationMain.Main> {
            MainScreen()
        }
    }
}

