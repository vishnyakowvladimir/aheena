package com.example.feature_motion_layout.presentation.container

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.core_api.di.extension.clearComponent
import com.example.core_api.di.extension.getComponent
import com.example.feature_motion_layout.di.MotionLayoutComponent
import com.example.feature_motion_layout.navigation.LocalDestinationMotionLayout
import com.example.feature_motion_layout.presentation.screen.MotionLayoutScreen
import com.example.lib_ui.utils.ComposableLifecycle

@Composable
internal fun MotionLayoutContainer() {
    val context = LocalContext.current
    context.getComponent<MotionLayoutComponent>()

    val navController = rememberNavController()

    ComposableLifecycle(
        onDestroy = {
            context.clearComponent<MotionLayoutComponent>()
        },
    )

    NavHost(
        navController = navController,
        startDestination = LocalDestinationMotionLayout.MotionLayout,
    ) {
        composable<LocalDestinationMotionLayout.MotionLayout> {
            MotionLayoutScreen()
        }
    }
}
