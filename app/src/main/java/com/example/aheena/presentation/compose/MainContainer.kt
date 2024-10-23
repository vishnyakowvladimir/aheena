package com.example.aheena.presentation.compose

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.example.aheena.navigation.FeatureComposablesHolder
import com.example.aheena.presentation.main_view_model.MainViewModel
import com.example.aheena.presentation.main_view_model.mvi.model.MainEvent

@Composable
internal fun MainContainer(
    navController: NavHostController,
    viewModelFactory: ViewModelProvider.Factory,
    composablesHolder: FeatureComposablesHolder,
    mainViewModel: MainViewModel,
) {
    BackHandler {
        mainViewModel.onEvent(MainEvent.Ui.OnBackPressed)
    }

    AppNavGraph(
        navController = navController,
        viewModelFactory = viewModelFactory,
        composablesHolder = composablesHolder,
    )
}
