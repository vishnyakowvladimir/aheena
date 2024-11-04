package com.example.feature_authentication.presentation.pin.mvi.handler

import com.example.core.controller.logout.LogoutController
import com.example.core.di.qualifier.MainRouter
import com.example.core.navigation.feature_destination.FeaturesDestination
import com.example.core.navigation.router.NavRouter
import com.example.feature_authentication.presentation.pin.mvi.model.PinEvent
import com.example.feature_authentication.presentation.pin.mvi.model.PinSideEffect
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
internal class PinUiSideEffectHandler @Inject constructor(
    @MainRouter private val mainRouter: NavRouter,
    private val logoutController: LogoutController,
) : SideEffectHandler<PinEvent, PinSideEffect.Ui> {
    private val sideEffectSharedFlow = MutableSharedFlow<PinSideEffect.Ui>(Int.MAX_VALUE)

    override fun getEventSource(): Flow<PinEvent> {
        return handleSideEffect()
    }

    override suspend fun onSideEffect(sideEffect: PinSideEffect.Ui) {
        sideEffectSharedFlow.emit(sideEffect)
    }

    private fun handleSideEffect(): Flow<PinEvent> {
        return sideEffectSharedFlow.flatMapMerge { sideEffect ->
            when (sideEffect) {
                is PinSideEffect.Ui.Back -> handleBack()
                is PinSideEffect.Ui.OpenMainScreen -> handleOpenMainScreen()
                is PinSideEffect.Ui.Logout -> handleLogout()
            }
        }
            .flowOn(Dispatchers.Main)
    }

    private fun handleBack(): Flow<PinEvent.Ui> {
        return flow {
            mainRouter.popBackStack()
            emit(PinEvent.Ui.None)
        }
    }

    private fun handleLogout(): Flow<PinEvent.Ui> {
        return flow {
            logoutController.logout()
            emit(PinEvent.Ui.None)
        }
    }

    private fun handleOpenMainScreen(): Flow<PinEvent.Ui> {
        return flow {
            mainRouter.replaceAll(FeaturesDestination.MainDestination)
            emit(PinEvent.Ui.None)
        }
    }
}

