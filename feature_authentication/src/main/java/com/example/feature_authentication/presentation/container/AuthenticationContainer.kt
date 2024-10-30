package com.example.feature_authentication.presentation.container

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.core.di.extension.clearComponent
import com.example.core.di.extension.getComponent
import com.example.feature_authentication.di.AuthenticationComponent
import com.example.feature_authentication.navigation.LocalDestionationAuthentication
import com.example.feature_authentication.presentation.biometric.BiometricsScreen
import com.example.feature_authentication.presentation.create_pin.CreatePinScreen
import com.example.feature_authentication.presentation.login.LoginScreen
import com.example.lib_ui.utils.ComposableLifecycle

@Composable
fun AuthenticationContainer() {
    val context = LocalContext.current
    val component = context.getComponent<AuthenticationComponent>()

    val navController = component.provideNavController()
    val viewModelFactory = component.provideViewModelFactory()
    val viewModel = viewModel<AuthenticationContainerViewModel>(factory = viewModelFactory)

    val startDestination = if (viewModel.isRefreshTokenExist()) {
        LocalDestionationAuthentication.PhoneAndPassword
    } else {
        LocalDestionationAuthentication.PhoneAndPassword
    }

    ComposableLifecycle(
        onDestroy = {
            context.clearComponent<AuthenticationComponent>()
        }
    )

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable<LocalDestionationAuthentication.PhoneAndPassword> {
            LoginScreen(viewModel = viewModel(factory = viewModelFactory))
        }

        composable<LocalDestionationAuthentication.CreatePin> {
            CreatePinScreen(viewModel = viewModel(factory = viewModelFactory))
        }

        composable<LocalDestionationAuthentication.Biometrics> {
            BiometricsScreen(viewModel = viewModel(factory = viewModelFactory))
        }
    }
}
