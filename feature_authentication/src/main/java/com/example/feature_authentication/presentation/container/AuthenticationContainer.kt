package com.example.feature_authentication.presentation.container

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.core.di.extension.clearComponent
import com.example.core.di.extension.getComponent
import com.example.feature_authentication.di.AuthenticationComponent
import com.example.feature_authentication.navigation.LocalDestinationAuthentication
import com.example.feature_authentication.presentation.biometric.BiometricsScreen
import com.example.feature_authentication.presentation.create_pin.CreatePinScreen
import com.example.feature_authentication.presentation.login.LoginScreen
import com.example.feature_authentication.presentation.pin.PinScreen
import com.example.lib_ui.utils.ComposableLifecycle

@Composable
fun AuthenticationContainer() {
    val context = LocalContext.current
    val component = context.getComponent<AuthenticationComponent>()

    val featureRouter = component.provideFeatureRouter()
    val viewModelFactory = component.provideViewModelFactory()
    val viewModel = viewModel<AuthenticationContainerViewModel>(factory = viewModelFactory)

    val navController = rememberNavController()
    featureRouter.setNavController(navController)

    val startDestination = if (viewModel.isRefreshTokenExist()) {
        LocalDestinationAuthentication.Pin
    } else {
        LocalDestinationAuthentication.PhoneAndPassword
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
        composable<LocalDestinationAuthentication.PhoneAndPassword> {
            LoginScreen(viewModel = viewModel(factory = viewModelFactory))
        }

        composable<LocalDestinationAuthentication.CreatePin> {
            CreatePinScreen(viewModel = viewModel(factory = viewModelFactory))
        }

        composable<LocalDestinationAuthentication.Biometrics> {
            BiometricsScreen(viewModel = viewModel(factory = viewModelFactory))
        }

        composable<LocalDestinationAuthentication.Pin> {
            PinScreen(viewModel = viewModel(factory = viewModelFactory))
        }
    }
}
