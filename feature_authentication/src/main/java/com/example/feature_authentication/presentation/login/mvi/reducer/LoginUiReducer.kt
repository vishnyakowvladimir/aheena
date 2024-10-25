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

internal class LoginUiReducer @Inject constructor() :
    Reducer<LoginEvent.Ui, LoginDomainState, LoginSideEffect, LoginUiCommand> {

    override fun update(
        state: LoginDomainState,
        event: LoginEvent.Ui,
    ): Update<LoginDomainState, LoginSideEffect, LoginUiCommand> {
        return when (event) {
            is LoginEvent.Ui.None -> Update.nothing()
            is LoginEvent.Ui.OnBackPressed -> reduceOnBackPressed()
            is LoginEvent.Ui.OnPhoneChanged -> reduceOnPhoneChanged(state, event)
            is LoginEvent.Ui.OnResetPhoneIconClick -> reduceOnResetPhoneIconClick(state)
            is LoginEvent.Ui.OnPasswordChanged -> reduceOnPasswordChanged(state, event)
            is LoginEvent.Ui.OnResetPasswordIconClick -> reduceOnResetPasswordIconClick(state)
            is LoginEvent.Ui.OnPasswordIconClick -> reduceOnPasswordIconClick(state)
            is LoginEvent.Ui.OnConfirmButtonClick -> reduceOnConfirmButtonClick(state)
        }
    }

    private fun reduceOnBackPressed(): Update<LoginDomainState, LoginSideEffect, LoginUiCommand> {
        return Update.sideEffects(
            sideEffects = listOf(LoginSideEffect.Ui.Back),
        )
    }

    private fun reduceOnPhoneChanged(
        state: LoginDomainState,
        event: LoginEvent.Ui.OnPhoneChanged,
    ): Update<LoginDomainState, LoginSideEffect, LoginUiCommand> {
        val phoneState = state.phoneState
        val passwordState = state.passwordState
        val buttonState = state.confirmButtonsState

        val updatedPhoneState = phoneState.copy(
            textFieldValue = event.value,
            showError = false,
        )
        val updatedPasswordState = passwordState.copy(showError = false)
        val updatedButtonState = buttonState.copy(
            isEnabled = updatedPhoneState.isFilled() && passwordState.isNotEmpty()
        )

        val updatedState = state.copy(
            phoneState = updatedPhoneState,
            passwordState = updatedPasswordState,
            confirmButtonsState = updatedButtonState,
        )

        return Update.state(updatedState)
    }

    private fun reduceOnResetPhoneIconClick(
        state: LoginDomainState,
    ): Update<LoginDomainState, LoginSideEffect, LoginUiCommand> {
        val phoneState = state.phoneState
        val passwordState = state.passwordState
        val buttonState = state.confirmButtonsState

        val updatedPhoneState = phoneState.copy(
            textFieldValue = TextFieldValue(annotatedString = AnnotatedString(text = "")),
            showError = false,
        )
        val updatedPasswordState = passwordState.copy(showError = false)
        val updatedButtonState = buttonState.copy(
            isEnabled = false,
        )

        val updatedState = state.copy(
            phoneState = updatedPhoneState,
            passwordState = updatedPasswordState,
            confirmButtonsState = updatedButtonState,
        )

        return Update.state(updatedState)
    }

    private fun reduceOnPasswordChanged(
        state: LoginDomainState,
        event: LoginEvent.Ui.OnPasswordChanged,
    ): Update<LoginDomainState, LoginSideEffect, LoginUiCommand> {
        val phoneState = state.phoneState
        val passwordState = state.passwordState
        val buttonState = state.confirmButtonsState

        val updatedPhoneState = phoneState.copy(showError = false)
        val updatedPasswordState = passwordState.copy(
            textFieldValue = event.value,
            showError = false,
        )
        val updatedButtonState = buttonState.copy(
            isEnabled = phoneState.isFilled() && updatedPasswordState.isNotEmpty()
        )

        val updatedState = state.copy(
            phoneState = updatedPhoneState,
            passwordState = updatedPasswordState,
            confirmButtonsState = updatedButtonState,
        )

        return Update.state(updatedState)
    }

    private fun reduceOnResetPasswordIconClick(
        state: LoginDomainState,
    ): Update<LoginDomainState, LoginSideEffect, LoginUiCommand> {
        val phoneState = state.phoneState
        val passwordState = state.passwordState
        val buttonState = state.confirmButtonsState

        val updatedPhoneState = phoneState.copy(showError = false)
        val updatedPasswordState = passwordState.copy(
            textFieldValue = TextFieldValue(annotatedString = AnnotatedString(text = "")),
            showError = false,
        )
        val updatedButtonState = buttonState.copy(
            isEnabled = false,
        )

        val updatedState = state.copy(
            phoneState = updatedPhoneState,
            passwordState = updatedPasswordState,
            confirmButtonsState = updatedButtonState,
        )

        return Update.state(updatedState)
    }

    private fun reduceOnPasswordIconClick(
        state: LoginDomainState,
    ): Update<LoginDomainState, LoginSideEffect, LoginUiCommand> {
        val passwordState = state.passwordState
        val updatedPasswordState = passwordState.copy(
            isPasswordVisible = !passwordState.isPasswordVisible,
        )
        val updatedState = state.copy(passwordState = updatedPasswordState)

        return Update.state(updatedState)
    }

    private fun reduceOnConfirmButtonClick(
        state: LoginDomainState,
    ): Update<LoginDomainState, LoginSideEffect, LoginUiCommand> {
        return when {
            state.isCorrect() -> {
                Update.sideEffects(
                    listOf(LoginSideEffect.Ui.OpenPinScreen),
                )
            }

            else -> {
                val passwordState = state.passwordState

                val updatedPasswordState = passwordState.copy(showError = true)
                val updatedState = state.copy(passwordState = updatedPasswordState)

                Update.state(updatedState)
            }
        }
    }
}
