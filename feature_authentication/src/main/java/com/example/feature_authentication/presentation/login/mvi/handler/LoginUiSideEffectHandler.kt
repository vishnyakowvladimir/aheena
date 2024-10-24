package com.example.feature_authentication.presentation.login.mvi.handler

import androidx.navigation.NavHostController
import com.example.core.navigation.router.AppRouter
import com.example.feature_authentication.navigation.LocalAuthenticationDestination
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
    private val router: AppRouter,
    private val navHostController: NavHostController,
) : SideEffectHandler<LoginEvent, LoginSideEffect.Ui> {
    private val sideEffectSharedFlow = MutableSharedFlow<LoginSideEffect.Ui>(Int.MAX_VALUE)

    override fun getEventSource(): Flow<LoginEvent> {
        return postListSideEffectHandler()
    }

    override suspend fun onSideEffect(sideEffect: LoginSideEffect.Ui) {
        sideEffectSharedFlow.emit(sideEffect)
    }

    private fun postListSideEffectHandler(): Flow<LoginEvent> {
        return sideEffectSharedFlow.flatMapMerge { sideEffect ->
            when (sideEffect) {
                is LoginSideEffect.Ui.Back -> handleBack()
                is LoginSideEffect.Ui.OpenPinScreen -> handleOpenPinScreen()

            }
        }
            .flowOn(Dispatchers.Main)
    }

    private fun handleBack(): Flow<LoginEvent.Ui> {
        return flow {
            router.popBackStack()
            emit(LoginEvent.Ui.None)
        }
    }

    private fun handleOpenPinScreen(): Flow<LoginEvent.Ui> {
        return flow {
            navHostController.navigate(LocalAuthenticationDestination.Pin)
            emit(LoginEvent.Ui.None)
        }
    }
}

