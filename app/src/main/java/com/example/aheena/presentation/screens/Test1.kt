package com.example.aheena.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.aheena.presentation.LocalNavController
import com.example.aheena.test.Profile

@Composable
fun Test1() {
    val navController = LocalNavController.current

    Text(
        text = "Go",
        modifier = Modifier.clickable {
//            navController.navigate(Test2NavigationEntry.Main.getNavigationRoute("Hello1", "Hello2"))
            navController.navigate(Profile("Hellp vova"))
        }
    )
}

@Composable
fun ProfileScreen(profile: Profile) {
    val navController = LocalNavController.current

    Text(
        text = profile.id,
        modifier = Modifier.clickable {
            navController.popBackStack()
        },
    )
}
