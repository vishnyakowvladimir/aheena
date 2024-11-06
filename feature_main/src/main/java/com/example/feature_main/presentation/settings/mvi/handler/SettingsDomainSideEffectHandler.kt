package com.example.feature_main.presentation.settings.mvi.handler

import com.example.feature_main.presentation.settings.mvi.model.SettingsEvent
import com.example.feature_main.presentation.settings.mvi.model.SettingsSideEffect
import com.example.mvi.SideEffectHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
internal class SettingsDomainSideEffectHandler @Inject constructor() :
    SideEffectHandler<SettingsEvent, SettingsSideEffect.Domain> {

    private val sideEffectSharedFlow = MutableSharedFlow<SettingsSideEffect.Domain>(Int.MAX_VALUE)

    override fun getEventSource(): Flow<SettingsEvent> {
        return handleSideEffect()
    }

    override suspend fun onSideEffect(sideEffect: SettingsSideEffect.Domain) {
        sideEffectSharedFlow.emit(sideEffect)
    }

    private fun handleSideEffect(): Flow<SettingsEvent> {
        return sideEffectSharedFlow.flatMapMerge { sideEffect ->
            emptyFlow<SettingsEvent>()
        }
            .flowOn(Dispatchers.IO)
    }
}

