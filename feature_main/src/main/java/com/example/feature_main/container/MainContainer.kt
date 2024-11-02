package com.example.feature_main.container

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.core.di.extension.clearComponent
import com.example.core.di.extension.getComponent
import com.example.feature_main.di.MainComponent
import com.example.lib_ui.theme.AppTheme
import com.example.lib_ui.utils.ComposableLifecycle
import com.example.lib_ui.utils.SetSystemBarsColor

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


    SetSystemBarsColor(
        statusBarColor = AppTheme.palette.background.primary,
    )

    BackHandler {
        viewModel.onBackPressed()
    }

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
        containerColor = AppTheme.palette.background.primary,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "Main screen")
        }
    }
}

