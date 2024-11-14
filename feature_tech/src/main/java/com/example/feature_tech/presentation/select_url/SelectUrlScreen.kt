package com.example.feature_tech.presentation.select_url

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core_api.utils.extension.collectAsStateLifecycleAware
import com.example.data_source_api.models.network.UrlsType
import com.example.feature_tech.R
import com.example.feature_tech.presentation.select_url.model.SelectUrlUiState
import com.example.feature_tech.presentation.select_url.mvi.model.SelectUrlEvent
import com.example.lib_ui.components.nav_bar.AppNavBar
import com.example.lib_ui.components.nav_bar.AppNavBarState
import com.example.lib_ui.theme.AppTheme
import com.example.lib_ui.utils.SetSystemBarsColor

@Composable
internal fun SelectUrlScreen(viewModel: SelectUrlViewModel) {
    val state = viewModel.uiState.collectAsStateLifecycleAware()

    SetSystemBarsColor(
        statusBarColor = AppTheme.palette.background.primary,
    )

    BackHandler {
        viewModel.onEvent(SelectUrlEvent.Ui.OnBackPressed)
    }

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
        topBar = {
            AppNavBar(
                state = AppNavBarState(
                    rightPart = null,
                    leftPart = null,
                    middlePart = AppNavBarState.MiddlePart(
                        title = stringResource(id = R.string.tech_select_url_title)
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
                .padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {
            Content(
                state = state.value,
                onUrlsTypeClick = { urlType ->
                    viewModel.onEvent(SelectUrlEvent.Ui.OnUrlsTypeClick(urlType))
                },
            )
        }
    }
}

@Composable
private fun Content(
    state: SelectUrlUiState,
    onUrlsTypeClick: (UrlsType) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        state.urlsTypeDataList.forEach { urlsTypeData ->
            RadioButtonBlock(
                state = urlsTypeData,
                onClick = onUrlsTypeClick,
            )
        }
    }
}

@Composable
private fun RadioButtonBlock(
    state: SelectUrlUiState.UrlsTypeData,
    onClick: (UrlsType) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = state.isSelected,
                onClick = {
                    onClick(state.urlsType)
                },
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        RadioButton(
            selected = state.isSelected,
            onClick = { onClick(state.urlsType) },
            colors = RadioButtonDefaults.colors().copy(
                selectedColor = AppTheme.palette.element.accent,
                unselectedColor = AppTheme.palette.element.primary,
            ),
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = state.title,
            style = AppTheme.typography.text1Regular,
            color = AppTheme.palette.text.secondary,
        )
    }
}
