package com.example.feature_authentication.presentation.phone_and_password.mvi.reducer

import com.example.feature_authentication.presentation.phone_and_password.mvi.model.LoginDomainState
import com.example.feature_authentication.presentation.phone_and_password.mvi.model.LoginEvent
import com.example.feature_authentication.presentation.phone_and_password.mvi.model.LoginSideEffect
import com.example.feature_authentication.presentation.phone_and_password.mvi.model.LoginUiCommand
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class LoginDomainReducer @Inject constructor() :
    Reducer<LoginEvent.Domain,
        LoginDomainState,
        LoginSideEffect,
        LoginUiCommand> {

    override fun update(
        state: LoginDomainState,
        event: LoginEvent.Domain
    ): Update<LoginDomainState, LoginSideEffect, LoginUiCommand> {
        return Update.nothing()
    }
}

