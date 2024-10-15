package com.example.aheena.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
internal fun AppNavGraph(navController: NavHostController) {

    CompositionLocalProvider(LocalNavController provides navController) {
        Text(text = "Hello")
//        NavHost(
//            navController = navController,
//            startDestination = AppScreens.MainScreen.route,
//        ) {
//            composable(AppScreens.MainScreen.route) {
//                MainScreen()
//            }
//            composable(AppScreens.ButtonsScreen.route) {
//                ButtonsScreen()
//            }
//            composable(AppScreens.InputsScreen.route) {
//                InputsScreen()
//            }
//            composable(AppScreens.PinCodeScreen.route) {
//                PinCodeScreen()
//            }
//            composable(AppScreens.BottomBarScreen.route) {
//                BottomBarScreen()
//            }
//        }
    }
}

val LocalNavController = compositionLocalOf<NavHostController> {
    error("LocalNavController error")
}
