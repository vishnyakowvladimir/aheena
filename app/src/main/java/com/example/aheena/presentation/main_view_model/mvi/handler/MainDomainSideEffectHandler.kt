package com.example.aheena.presentation.main_view_model.mvi.handler

import com.example.aheena.presentation.main_view_model.mvi.model.MainEvent
import com.example.aheena.presentation.main_view_model.mvi.model.MainSideEffect
import com.example.core.presentation.theme_manager.ThemeManager
import com.example.core.presentation.theme_manager.mapToDomain
import com.example.core.presentation.theme_manager.mapToUi
import com.example.mvi.SideEffectHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
internal class MainDomainSideEffectHandler @Inject constructor(
    private val themeManager: ThemeManager,
) :
    SideEffectHandler<MainEvent, MainSideEffect.Domain> {

    private val sideEffectSharedFlow = MutableSharedFlow<MainSideEffect.Domain>(Int.MAX_VALUE)

    override fun getEventSource(): Flow<MainEvent> {
        return postListSideEffectHandler()
    }

    override suspend fun onSideEffect(sideEffect: MainSideEffect.Domain) {
        sideEffectSharedFlow.emit(sideEffect)
    }

    private fun postListSideEffectHandler(): Flow<MainEvent> {
        return sideEffectSharedFlow.flatMapMerge { sideEffect ->
            when (sideEffect) {
                is MainSideEffect.Domain.LoadData -> handleLoadData()
                is MainSideEffect.Domain.ChangeViewScale -> handleChangeViewScale(sideEffect)
            }
        }
            .flowOn(Dispatchers.IO)
    }

    private fun handleLoadData(): Flow<MainEvent.Domain> {
        return flow {
            delay(1000)
            emit(MainEvent.Domain.OnDataLoaded)
        }
    }

    private fun handleChangeViewScale(
        sideEffect: MainSideEffect.Domain.ChangeViewScale,
    ): Flow<MainEvent.Domain> {
        return flow {
            themeManager.saveScale(sideEffect.viewScaleDomain.mapToUi())
            emit(MainEvent.Domain.OnScaleChanged(themeManager.getScale().mapToDomain()))
        }
    }
}

