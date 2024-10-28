package com.example.feature_authentication.presentation.login.mvi.handler

import com.example.feature_authentication.domain.LocalAuthenticationInteractor
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
internal class LoginDomainSideEffectHandler @Inject constructor(
    private val localAuthenticationInteractor: LocalAuthenticationInteractor,
) :
    SideEffectHandler<LoginEvent, LoginSideEffect.Domain> {

    private val sideEffectSharedFlow = MutableSharedFlow<LoginSideEffect.Domain>(Int.MAX_VALUE)

    override fun getEventSource(): Flow<LoginEvent> {
        return postListSideEffectHandler()
    }

    override suspend fun onSideEffect(sideEffect: LoginSideEffect.Domain) {
        sideEffectSharedFlow.emit(sideEffect)
    }

    private fun postListSideEffectHandler(): Flow<LoginEvent> {
        return sideEffectSharedFlow.flatMapMerge { sideEffect ->
            when (sideEffect) {
                is LoginSideEffect.Domain.SaveRefreshToken -> handleSaveRefreshToken(sideEffect)
            }
        }
            .flowOn(Dispatchers.IO)
    }

    private fun handleSaveRefreshToken(
        sideEffect: LoginSideEffect.Domain.SaveRefreshToken,
    ): Flow<LoginEvent> {
        return flow {
            localAuthenticationInteractor.saveRefreshToken(sideEffect.refreshToken)
            emit(LoginEvent.Domain.None)
        }
    }
}

