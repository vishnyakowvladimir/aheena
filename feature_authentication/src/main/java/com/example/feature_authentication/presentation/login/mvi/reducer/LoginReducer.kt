package com.example.feature_authentication.presentation.login.mvi.reducer

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import com.example.feature_authentication.presentation.login.mvi.model.LoginDomainState
import com.example.feature_authentication.presentation.login.mvi.model.LoginEvent
import com.example.feature_authentication.presentation.login.mvi.model.LoginSideEffect
import com.example.feature_authentication.presentation.login.mvi.model.LoginUiCommand
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class LoginReducer @Inject constructor(
    private val uiReducer: LoginUiReducer,
    private val domainReducer: LoginDomainReducer,
) : Reducer<LoginEvent, LoginDomainState, LoginSideEffect, LoginUiCommand> {

    override fun update(
        state: LoginDomainState,
        event: LoginEvent,
    ): Update<LoginDomainState, LoginSideEffect, LoginUiCommand> {
        return when (event) {
            is LoginEvent.Ui -> uiReducer.update(state, event)
            is LoginEvent.Domain -> domainReducer.update(state, event)
        }
    }

    fun getInitialState(): LoginDomainState {
        return LoginDomainState(
            phoneState = LoginDomainState.PhoneState(
                textFieldValue = TextFieldValue(annotatedString = AnnotatedString(text = "")),
                showError = false,
            ),
            passwordState = LoginDomainState.PasswordState(
                textFieldValue = TextFieldValue(annotatedString = AnnotatedString(text = "")),
                isPasswordVisible = false,
                showError = false,
            ),
            confirmButtonsState = LoginDomainState.ConfirmButtonState(
                isEnabled = false,
            )
        )
    }
}
