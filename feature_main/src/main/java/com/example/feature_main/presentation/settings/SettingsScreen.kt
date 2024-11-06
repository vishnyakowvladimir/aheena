package com.example.feature_main.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.utils.extension.collectAsStateLifecycleAware
import com.example.feature_main.R
import com.example.feature_main.presentation.settings.mvi.model.SettingsEvent
import com.example.lib_ui.theme.AppTheme

@Composable
internal fun SettingsScreen(viewModel: SettingsViewModel) {
    val state = viewModel.uiState.collectAsStateLifecycleAware()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {

        }

        LogoutBlock(
            onClick = {
                viewModel.onEvent(SettingsEvent.Ui.OnLogoutClick)
            },
        )
    }
}

@Composable
private fun LogoutBlock(
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(color = AppTheme.palette.text.secondary),
        )

        Text(
            text = stringResource(id = R.string.settings_logout_button),
            style = AppTheme.typography.text1Regular,
            color = AppTheme.palette.text.secondary,
            modifier = Modifier
                .clickable { onClick() }
                .padding(vertical = 12.dp),
        )
    }
}

