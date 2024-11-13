package com.example.feature_main.presentation.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.core_api.di.extension.getComponent
import com.example.core_api.utils.extension.collectAsStateLifecycleAware
import com.example.feature_main.di.MainComponent
import com.example.feature_main.presentation.features.FeaturesScreen
import com.example.feature_main.presentation.main.model.MainUiState
import com.example.feature_main.presentation.main.model.PagerScreen
import com.example.feature_main.presentation.main.mvi.model.MainEvent
import com.example.feature_main.presentation.settings.SettingsScreen
import com.example.lib_ui.components.bottom_bar.AppBottomBar
import com.example.lib_ui.theme.AppTheme
import com.example.lib_ui.utils.SetSystemBarsColor

@Composable
internal fun MainScreen(viewModel: MainViewModel) {

    val state = viewModel.uiState.collectAsStateLifecycleAware()

    SetSystemBarsColor(
        statusBarColor = AppTheme.palette.background.primary,
    )

    BackHandler {
        viewModel.onEvent(MainEvent.Ui.OnBackPressed)
    }

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
        containerColor = AppTheme.palette.background.primary,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {
            Content(
                state = state.value,
                onBottomBarItemClick = { index ->
                    viewModel.onEvent(MainEvent.Ui.OnBottomBarItemClick(index))
                },
            )
        }
    }
}

@Composable
private fun Content(
    state: MainUiState,
    onBottomBarItemClick: (Int) -> Unit,
) {

    val viewModelFactory = LocalContext.current.getComponent<MainComponent>().provideViewModelFactory()

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        HorizontalPager(
            state = state.pagerState,
            userScrollEnabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) { pageIndex ->
            when (pageIndex) {
                PagerScreen.FEATURES.index -> {
                    FeaturesScreen(viewModel = viewModel(factory = viewModelFactory))
                }

                PagerScreen.SETTINGS.index -> {
                    SettingsScreen(viewModel = viewModel(factory = viewModelFactory))
                }

                else -> Unit
            }
        }

        AppBottomBar(
            state = state.bottomBarState,
            onItemClick = onBottomBarItemClick,
        )
    }
}
