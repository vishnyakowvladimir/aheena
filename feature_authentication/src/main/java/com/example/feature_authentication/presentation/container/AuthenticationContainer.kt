package com.example.feature_authentication.presentation.container

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.core.di.extension.clearComponent
import com.example.core.di.extension.getComponent
import com.example.feature_authentication.di.AuthenticationComponent
import com.example.feature_authentication.navigation.LocalDestination
import com.example.feature_authentication.presentation.phone_and_password.PhoneAndPasswordScreen

@Composable
fun AuthenticationContainer() {
    val context = LocalContext.current
    
    val navController = context.getComponent<AuthenticationComponent>().provideNavController()

    DisposableEffect(Unit) {
        onDispose {
            context.clearComponent<AuthenticationComponent>()
        }
    }

    NavHost(
        navController = navController,
        startDestination = LocalDestination.PhoneAndPassword,
    ) {
        composable<LocalDestination.PhoneAndPassword> {
            PhoneAndPasswordScreen()
        }

        composable<LocalDestination.Pin> {

        }
    }
}
