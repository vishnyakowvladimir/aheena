package com.example.feature_authentication.presentation.login.mvi.handler

import com.example.core_api.di.qualifier.MainRouter
import com.example.core_api.navigation.router.NavRouter
import com.example.feature_authentication.navigation.LocalDestinationAuthentication
import com.example.feature_authentication.presentation.login.mvi.model.LoginEvent
import com.example.feature_authentication.presentation.login.mvi.model.LoginSideEffect
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
internal class LoginUiSideEffectHandler @Inject constructor(
    @MainRouter private val mainRouter: NavRouter,
    private val router: NavRouter,
) : SideEffectHandler<LoginEvent, LoginSideEffect.Ui> {
    private val sideEffectSharedFlow = MutableSharedFlow<LoginSideEffect.Ui>(Int.MAX_VALUE)

    override fun getEventSource(): Flow<LoginEvent> {
        return handleSideEffect()
    }

    override suspend fun onSideEffect(sideEffect: LoginSideEffect.Ui) {
        sideEffectSharedFlow.emit(sideEffect)
    }

    private fun handleSideEffect(): Flow<LoginEvent> {
        return sideEffectSharedFlow.flatMapMerge { sideEffect ->
            when (sideEffect) {
                is LoginSideEffect.Ui.Back -> handleBack()
                is LoginSideEffect.Ui.OpenPinScreen -> handleOpenPinScreen()

            }
        }
            .flowOn(Dispatchers.Main)
    }

    private fun handleBack(): Flow<LoginEvent> {
        return flow {
            mainRouter.popBackStack()
            emit(LoginEvent.None)
        }
    }

    private fun handleOpenPinScreen(): Flow<LoginEvent> {
        return flow {
            router.navigate(LocalDestinationAuthentication.CreatePin)
            emit(LoginEvent.None)
        }
    }
}

