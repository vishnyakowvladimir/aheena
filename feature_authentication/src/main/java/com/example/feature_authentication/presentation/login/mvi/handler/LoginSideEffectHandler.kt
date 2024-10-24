package com.example.feature_authentication.presentation.login.mvi.handler

import com.example.feature_authentication.presentation.login.mvi.model.LoginEvent
import com.example.feature_authentication.presentation.login.mvi.model.LoginSideEffect
import com.example.mvi.SideEffectHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

internal class LoginSideEffectHandler @Inject constructor(
    private val uiHandler: LoginUiSideEffectHandler,
    private val domainHandler: LoginDomainSideEffectHandler,
) : SideEffectHandler<LoginEvent, LoginSideEffect> {

    override fun getEventSource(): Flow<LoginEvent> {
        return merge(
            uiHandler.getEventSource(),
            domainHandler.getEventSource(),
        )
    }

    override suspend fun onSideEffect(sideEffect: LoginSideEffect) {
        when (sideEffect) {
            is LoginSideEffect.Ui -> uiHandler.onSideEffect(sideEffect)
            is LoginSideEffect.Domain -> domainHandler.onSideEffect(sideEffect)
        }
    }
}
