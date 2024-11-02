package com.example.feature_authentication.presentation.create_pin.mvi.handler

import com.example.core.di.qualifier.FeatureRouter
import com.example.core.di.qualifier.MainRouter
import com.example.core.navigation.feature_destination.FeaturesDestination
import com.example.core.navigation.router.NavRouter
import com.example.feature_authentication.navigation.LocalDestinationAuthentication
import com.example.feature_authentication.presentation.create_pin.mvi.model.CreatePinEvent
import com.example.feature_authentication.presentation.create_pin.mvi.model.CreatePinSideEffect
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
internal class CreatePinUiSideEffectHandler @Inject constructor(
    @MainRouter private val mainRouter: NavRouter,
    @FeatureRouter private val router: NavRouter,
) : SideEffectHandler<CreatePinEvent, CreatePinSideEffect.Ui> {
    private val sideEffectSharedFlow = MutableSharedFlow<CreatePinSideEffect.Ui>(Int.MAX_VALUE)

    override fun getEventSource(): Flow<CreatePinEvent> {
        return postListSideEffectHandler()
    }

    override suspend fun onSideEffect(sideEffect: CreatePinSideEffect.Ui) {
        sideEffectSharedFlow.emit(sideEffect)
    }

    private fun postListSideEffectHandler(): Flow<CreatePinEvent> {
        return sideEffectSharedFlow.flatMapMerge { sideEffect ->
            when (sideEffect) {
                is CreatePinSideEffect.Ui.Back -> handleBack()
                is CreatePinSideEffect.Ui.DelayBeforeChangeMode -> handleDelayBeforeChangeMode()
                is CreatePinSideEffect.Ui.OpenMainScreen -> handleOpenMainScreen()
                is CreatePinSideEffect.Ui.OpenBiometricScreen -> handleOpenBiometricScreen()
            }
        }
            .flowOn(Dispatchers.Main)
    }

    private fun handleBack(): Flow<CreatePinEvent.Ui> {
        return flow {
            router.popBackStack()
            emit(CreatePinEvent.Ui.None)
        }
    }

    private fun handleDelayBeforeChangeMode(): Flow<CreatePinEvent.Ui> {
        return flow {
            delay(400)
            emit(CreatePinEvent.Ui.OnDelayBeforeChangeMode)
        }
    }

    private fun handleOpenMainScreen(): Flow<CreatePinEvent.Ui> {
        return flow {
            mainRouter.replaceAll(FeaturesDestination.MainDestination)
            emit(CreatePinEvent.Ui.None)
        }
    }

    private fun handleOpenBiometricScreen(): Flow<CreatePinEvent.Ui> {
        return flow {
            router.replaceAll(LocalDestinationAuthentication.Biometrics)
            emit(CreatePinEvent.Ui.None)
        }
    }
}

