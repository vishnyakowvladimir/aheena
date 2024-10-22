package com.example.aheena.presentation.compose

import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.aheena.navigation.FeatureComposablesHolder
import com.example.aheena.presentation.main_view_model.MainViewModel
import com.example.aheena.presentation.main_view_model.mvi.model.MainEvent
import com.example.lib_ui.containers.ThemeContainer
import com.example.lib_ui.theme.AppThemeMode
import com.example.lib_ui.theme.typography.ViewScale

@Composable
internal fun MainContainer(
    navController: NavHostController,
    viewModelFactory: ViewModelProvider.Factory,
    composablesHolder: FeatureComposablesHolder,
    mainViewModel: MainViewModel,
) {
    val state = mainViewModel.uiState.collectAsStateWithLifecycle()

    BackHandler {
        mainViewModel.onEvent(MainEvent.Ui.OnBackPressed)
    }

    val themeState = state.value.themeState
    ThemeContainer(
        activity = LocalContext.current as AppCompatActivity,
        viewScale = ViewScale.M,
        themeMode = AppThemeMode.LIGHT,
    ) {
        AppNavGraph(
            navController = navController,
            viewModelFactory = viewModelFactory,
            composablesHolder = composablesHolder,
        )
    }
}
