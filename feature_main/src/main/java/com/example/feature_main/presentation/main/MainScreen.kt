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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.core.utils.extension.collectAsStateLifecycleAware
import com.example.feature_main.presentation.main.model.MainUiState
import com.example.feature_main.presentation.main.mvi.model.MainEvent
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
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            Text(text = "Hello")
        }

        AppBottomBar(
            state = state.bottomBarState,
            onItemClick = onBottomBarItemClick,
        )
    }
}
