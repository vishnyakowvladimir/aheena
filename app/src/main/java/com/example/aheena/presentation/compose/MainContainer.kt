package com.example.aheena.presentation.compose

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import com.example.aheena.navigation.FeatureComposablesHolder
import com.example.aheena.presentation.main_view_model.MainViewModel
import com.example.aheena.presentation.main_view_model.mvi.model.MainEvent
import com.example.core_impl.navigation.NavControllerHolder

@Composable
internal fun MainContainer(
    navControllerHolder: NavControllerHolder,
    viewModelFactory: ViewModelProvider.Factory,
    composablesHolder: FeatureComposablesHolder,
    mainViewModel: MainViewModel,
) {
    BackHandler {
        mainViewModel.onEvent(MainEvent.Ui.OnBackPressed)
    }

    AppNavGraph(
        navControllerHolder = navControllerHolder,
        viewModelFactory = viewModelFactory,
        composablesHolder = composablesHolder,
    )
}
