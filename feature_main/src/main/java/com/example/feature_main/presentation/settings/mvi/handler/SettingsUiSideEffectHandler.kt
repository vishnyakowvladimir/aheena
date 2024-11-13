package com.example.feature_main.presentation.settings.mvi.handler

import com.example.core_api.controller.logout.LogoutController
import com.example.core_api.controller.theme.ThemeManager
import com.example.feature_main.presentation.settings.mvi.model.SettingsEvent
import com.example.feature_main.presentation.settings.mvi.model.SettingsSideEffect
import com.example.mvi.SideEffectHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
internal class SettingsUiSideEffectHandler @Inject constructor(
    private val logoutController: LogoutController,
    private val themeManager: ThemeManager,
) : SideEffectHandler<SettingsEvent, SettingsSideEffect.Ui> {
    private val sideEffectSharedFlow = MutableSharedFlow<SettingsSideEffect.Ui>(Int.MAX_VALUE)

    override fun getEventSource(): Flow<SettingsEvent> {
        return handleSideEffect()
    }

    override suspend fun onSideEffect(sideEffect: SettingsSideEffect.Ui) {
        sideEffectSharedFlow.emit(sideEffect)
    }

    private fun handleSideEffect(): Flow<SettingsEvent> {
        return sideEffectSharedFlow.flatMapMerge { sideEffect ->
            when (sideEffect) {
                SettingsSideEffect.Ui.Logout -> handleLogout()
                SettingsSideEffect.Ui.ApplyThemeLight -> handleApplyThemeLight()
                SettingsSideEffect.Ui.ApplyThemeDark -> handleApplyThemeDark()
                SettingsSideEffect.Ui.ApplyThemeSystem -> handleApplyThemeSystem()
            }
        }
            .flowOn(Dispatchers.Main)
    }

    private fun handleLogout(): Flow<SettingsEvent> {
        return flow {
            logoutController.logout()
            emit(SettingsEvent.None)
        }
    }

    private fun handleApplyThemeLight(): Flow<SettingsEvent> {
        return flow {
            themeManager.applyThemeModeLight()
            emit(SettingsEvent.None)
        }
    }

    private fun handleApplyThemeDark(): Flow<SettingsEvent> {
        return flow {
            themeManager.applyThemeModeDark()
            emit(SettingsEvent.None)
        }
    }

    private fun handleApplyThemeSystem(): Flow<SettingsEvent> {
        return flow {
            themeManager.applyThemeModeSystem()
            emit(SettingsEvent.None)
        }
    }
}

