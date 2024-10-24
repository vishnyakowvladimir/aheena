package com.example.feature_authentication.presentation.container

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.core.di.extension.clearComponent
import com.example.core.di.extension.getComponent
import com.example.feature_authentication.di.AuthenticationComponent
import com.example.feature_authentication.navigation.LocalAuthenticationDestination
import com.example.feature_authentication.presentation.phone_and_password.LoginScreen
import com.example.lib_ui.utils.ComposableLifecycle

@Composable
fun AuthenticationContainer() {
    val context = LocalContext.current
    val component = context.getComponent<AuthenticationComponent>()

    val navController = component.provideNavController()

    ComposableLifecycle(
        onDestroy = {
            context.clearComponent<AuthenticationComponent>()
        }
    )

    NavHost(
        navController = navController,
        startDestination = LocalAuthenticationDestination.PhoneAndPassword,
    ) {
        composable<LocalAuthenticationDestination.PhoneAndPassword> {
            LoginScreen(
                viewModel = viewModel()
            )
        }

        composable<LocalAuthenticationDestination.Pin> {

        }
    }
}
