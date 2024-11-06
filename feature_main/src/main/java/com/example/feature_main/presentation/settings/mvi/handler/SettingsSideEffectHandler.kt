package com.example.feature_main.presentation.settings.mvi.handler

import com.example.feature_main.presentation.settings.mvi.model.SettingsEvent
import com.example.feature_main.presentation.settings.mvi.model.SettingsSideEffect
import com.example.mvi.SideEffectHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

internal class SettingsSideEffectHandler @Inject constructor(
    private val uiHandler: SettingsUiSideEffectHandler,
    private val domainHandler: SettingsDomainSideEffectHandler,
) : SideEffectHandler<SettingsEvent, SettingsSideEffect> {

    override fun getEventSource(): Flow<SettingsEvent> {
        return merge(
            uiHandler.getEventSource(),
            domainHandler.getEventSource(),
        )
    }

    override suspend fun onSideEffect(sideEffect: SettingsSideEffect) {
        when (sideEffect) {
            is SettingsSideEffect.Ui -> uiHandler.onSideEffect(sideEffect)
            is SettingsSideEffect.Domain -> domainHandler.onSideEffect(sideEffect)
            else -> Unit
        }
    }
}
