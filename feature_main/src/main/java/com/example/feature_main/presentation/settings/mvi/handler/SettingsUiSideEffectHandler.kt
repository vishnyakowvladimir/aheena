package com.example.feature_main.presentation.settings.mvi.handler

import com.example.core.controller.logout.LogoutController
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
}

