package com.example.feature_authentication.presentation.biometric.mvi.handler

import androidx.navigation.NavHostController
import com.example.core.navigation.router.AppRouter
import com.example.feature_authentication.presentation.biometric.mvi.model.BiometricsEvent
import com.example.feature_authentication.presentation.biometric.mvi.model.BiometricsSideEffect
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
internal class BiometricsUiSideEffectHandler @Inject constructor(
    private val router: AppRouter,
    private val navHostController: NavHostController,
) : SideEffectHandler<BiometricsEvent, BiometricsSideEffect.Ui> {
    private val sideEffectSharedFlow = MutableSharedFlow<BiometricsSideEffect.Ui>(Int.MAX_VALUE)

    override fun getEventSource(): Flow<BiometricsEvent> {
        return postListSideEffectHandler()
    }

    override suspend fun onSideEffect(sideEffect: BiometricsSideEffect.Ui) {
        sideEffectSharedFlow.emit(sideEffect)
    }

    private fun postListSideEffectHandler(): Flow<BiometricsEvent> {
        return sideEffectSharedFlow.flatMapMerge { sideEffect ->
            when (sideEffect) {
                is BiometricsSideEffect.Ui.Back -> handleBack()
                is BiometricsSideEffect.Ui.OpenMainScreen -> handleOpenMainScreen()
            }
        }
            .flowOn(Dispatchers.Main)
    }

    private fun handleBack(): Flow<BiometricsEvent.Ui> {
        return flow {
            navHostController.popBackStack()
            emit(BiometricsEvent.Ui.None)
        }
    }

    private fun handleOpenMainScreen(): Flow<BiometricsEvent.Ui> {
        return flow {
            navHostController.popBackStack()
            emit(BiometricsEvent.Ui.None)
        }
    }
}

