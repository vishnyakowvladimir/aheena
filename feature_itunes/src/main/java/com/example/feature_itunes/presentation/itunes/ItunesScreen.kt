package com.example.feature_itunes.presentation.itunes

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.utils.extension.collectAsStateLifecycleAware
import com.example.domain_models.itunes.ItunesTrack
import com.example.feature_itunes.R
import com.example.feature_itunes.presentation.itunes.model.ItunesData
import com.example.feature_itunes.presentation.itunes.model.ItunesUiState
import com.example.feature_itunes.presentation.itunes.mvi.model.ItunesEvent
import com.example.lib_ui.components.loader.GradientCircularLoader
import com.example.lib_ui.components.nav_bar.AppNavBar
import com.example.lib_ui.components.nav_bar.AppNavBarState
import com.example.lib_ui.theme.AppTheme
import com.example.lib_ui.utils.SetSystemBarsColor
import com.example.lib_ui.R as LibUiR

@Composable
internal fun ItunesScreen(viewModel: ItunesViewModel) {
    val state = viewModel.uiState.collectAsStateLifecycleAware()

    SetSystemBarsColor(
        statusBarColor = AppTheme.palette.background.primary,
    )

    BackHandler {
        viewModel.onEvent(ItunesEvent.Ui.OnBackPressed)
    }

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
        topBar = {
            AppNavBar(
                state = AppNavBarState(
                    rightPart = null,
                    leftPart = AppNavBarState.LeftPart(
                        iconRes = LibUiR.drawable.ic_24dp_navigation_back,
                        onClick = {
                            viewModel.onEvent(ItunesEvent.Ui.OnBackPressed)
                        },
                    ),
                    middlePart = AppNavBarState.MiddlePart(
                        title = stringResource(id = R.string.itunes_title)
                    ),
                    backgroundColor = AppTheme.palette.background.primary,
                ),
            )
        },
        containerColor = AppTheme.palette.background.primary,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(12.dp),
        ) {
            Content(
                state = state.value,
            )
        }
    }
}

@Composable
private fun Content(state: ItunesUiState) {
    when (state.data) {
        is ItunesData.Loading -> {
            Loading()
        }

        is ItunesData.Error -> {}

        is ItunesData.Data -> {
            DataContent(data = state.data)
        }
    }
}

@Composable
private fun Loading() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        GradientCircularLoader.XL(color = AppTheme.palette.element.accent)
    }
}

@Composable
private fun DataContent(data: ItunesData.Data) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        data.tracks.forEachIndexed { index, track ->
            Cell(track = track)

            if (index <= data.tracks.size - 2) {
                Divider()
            }
        }
    }
}

@Composable
private fun Cell(
    track: ItunesTrack,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Text(
            text = track.name,
            color = AppTheme.palette.text.primary,
            style = AppTheme.typography.text1Regular,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = track.name,
            color = AppTheme.palette.text.secondary,
            style = AppTheme.typography.text1Regular,
        )
    }
}

@Composable
private fun Divider() {
    Box(
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(color = AppTheme.palette.icon.secondary),
    )
}
