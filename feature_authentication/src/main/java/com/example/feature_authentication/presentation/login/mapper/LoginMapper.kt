package com.example.feature_authentication.presentation.login.mapper

import com.example.core.utils.string_provider.StringProvider
import com.example.feature_authentication.R
import com.example.feature_authentication.presentation.login.model.LoginUiState
import com.example.feature_authentication.presentation.login.mvi.model.LoginDomainState
import javax.inject.Inject

internal class LoginMapper @Inject constructor(
    private val stringProvider: StringProvider,
) {
    fun map(domainState: LoginDomainState): LoginUiState {
        return LoginUiState(
            phoneState = mapPhoneState(domainState.phoneState),
            passwordState = mapPasswordState(domainState.passwordState),
            confirmButtonsState = mapConfirmButtonState(domainState.confirmButtonsState),
        )
    }

    private fun mapPhoneState(state: LoginDomainState.PhoneState): LoginUiState.PhoneState {
        return LoginUiState.PhoneState(
            textFieldValue = state.textFieldValue,
            placeholderText = stringProvider.getString(R.string.authentication_login_phone_placeholder),
            errorText = stringProvider.getString(R.string.authentication_login_phone_error),
            isError = state.isError,
        )
    }

    private fun mapPasswordState(state: LoginDomainState.PasswordState): LoginUiState.PasswordState {
        return LoginUiState.PasswordState(
            textFieldValue = state.textFieldValue,
            placeholderText = stringProvider.getString(R.string.authentication_login_password_placeholder),
            errorText = stringProvider.getString(R.string.authentication_login_password_error),
            isPasswordVisible = state.isPasswordVisible,
            isError = state.isError,
        )
    }

    private fun mapConfirmButtonState(state: LoginDomainState.ConfirmButtonState): LoginUiState.ConfirmButtonState {
        return LoginUiState.ConfirmButtonState(
            title = stringProvider.getString(R.string.authentication_login_confirm_button_title),
            isEnabled = state.isEnabled,
        )
    }
}
