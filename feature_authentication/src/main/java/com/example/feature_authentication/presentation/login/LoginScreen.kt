package com.example.feature_authentication.presentation.login

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.utils.extension.collectAsStateLifecycleAware
import com.example.feature_authentication.R
import com.example.feature_authentication.presentation.login.model.LoginUiState
import com.example.feature_authentication.presentation.login.mvi.model.LoginEvent
import com.example.lib_ui.components.button.AppButton
import com.example.lib_ui.components.button.AppButtonState
import com.example.lib_ui.components.input.password_input.PasswordInput
import com.example.lib_ui.components.input.password_input.PasswordInputState
import com.example.lib_ui.components.input.phone_input.PhoneInput
import com.example.lib_ui.components.input.phone_input.PhoneInputState
import com.example.lib_ui.components.nav_bar.AppNavBar
import com.example.lib_ui.components.nav_bar.AppNavBarState
import com.example.lib_ui.theme.AppTheme
import com.example.lib_ui.theme.AppThemeContainer
import com.example.lib_ui.theme.typography.ViewScale
import com.example.lib_ui.utils.SetSystemBarsColor

@Composable
internal fun LoginScreen(
    viewModel: LoginViewModel,
) {
    val state = viewModel.uiState.collectAsStateLifecycleAware()

    SetSystemBarsColor(
        statusBarColor = AppTheme.palette.background.primary,
    )

    BackHandler {
        viewModel.onEvent(LoginEvent.Ui.OnBackPressed)
    }

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
        topBar = {
            AppNavBar(
                state = AppNavBarState(
                    leftPart = null,
                    rightPart = null,
                    middlePart = AppNavBarState.MiddlePart(
                        title = stringResource(id = R.string.authentication_login_title)
                    ),
                    backgroundColor = AppTheme.palette.background.primary,
                ),
            )
        },
        containerColor = AppTheme.palette.background.primary,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Content(
                state = state.value,
                onPhoneChanged = { textFieldValue ->
                    viewModel.onEvent(LoginEvent.Ui.OnPhoneChanged(textFieldValue))
                },
                onResetPhoneIconClick = {
                    viewModel.onEvent(LoginEvent.Ui.OnResetPhoneIconClick)
                },
                onPasswordChanged = { textFieldValue ->
                    viewModel.onEvent(LoginEvent.Ui.OnPasswordChanged(textFieldValue))
                },
                onResetPasswordIconClick = {
                    viewModel.onEvent(LoginEvent.Ui.OnResetPasswordIconClick)
                },
                onPasswordIconClick = {
                    viewModel.onEvent(LoginEvent.Ui.OnPasswordIconClick)
                },
                onConfirmButtonClick = {
                    viewModel.onEvent(LoginEvent.Ui.OnConfirmButtonClick)
                },
            )
        }
    }
}

@Composable
private fun Content(
    state: LoginUiState,
    onPhoneChanged: (TextFieldValue) -> Unit,
    onResetPhoneIconClick: () -> Unit,
    onPasswordChanged: (TextFieldValue) -> Unit,
    onResetPasswordIconClick: () -> Unit,
    onPasswordIconClick: () -> Unit,
    onConfirmButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            PhoneAndPasswordBlock(
                state = state,
                onPhoneChanged = onPhoneChanged,
                onResetPhoneIconClick = onResetPhoneIconClick,
                onPasswordChanged = onPasswordChanged,
                onResetPasswordIconClick = onResetPasswordIconClick,
                onPasswordIconClick = onPasswordIconClick,
            )
        }

        AppButton(
            modifier = Modifier.fillMaxWidth(),
            state = AppButtonState(
                title = state.confirmButtonsState.title,
                isEnabled = state.confirmButtonsState.isEnabled
            ),
            onClick = onConfirmButtonClick,
        )
    }
}

@Composable
private fun PhoneAndPasswordBlock(
    state: LoginUiState,
    onPhoneChanged: (TextFieldValue) -> Unit,
    onResetPhoneIconClick: () -> Unit,
    onPasswordChanged: (TextFieldValue) -> Unit,
    onResetPasswordIconClick: () -> Unit,
    onPasswordIconClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        PhoneInputBlock(
            phoneState = state.phoneState,
            onPhoneChanged = onPhoneChanged,
            onResetPhoneIconClick = onResetPhoneIconClick,
        )

        Spacer(modifier = Modifier.height(12.dp))

        PasswordInputBlock(
            state = state.passwordState,
            onPasswordChanged = onPasswordChanged,
            onResetPasswordIconClick = onResetPasswordIconClick,
            onPasswordIconClick = onPasswordIconClick,
        )
    }
}

@Composable
private fun PhoneInputBlock(
    phoneState: LoginUiState.PhoneState,
    onPhoneChanged: (TextFieldValue) -> Unit,
    onResetPhoneIconClick: () -> Unit,
) {
    val localFocusManager = LocalFocusManager.current

    PhoneInput(
        state = PhoneInputState(
            mainState = PhoneInputState.MainState(
                textFieldValue = phoneState.textFieldValue,
                placeholderText = phoneState.placeholderText,
                onValueChange = { textFieldValue ->
                    onPhoneChanged(textFieldValue)
                },
                keyboardActions = KeyboardActions(
                    onNext = {
                        localFocusManager.moveFocus(FocusDirection.Down)
                    },
                ),
                imeAction = ImeAction.Next,
            ),
            tailState = PhoneInputState.TailState(
                onResetIconClick = onResetPhoneIconClick,
            ),
            errorState = PhoneInputState.ErrorState(
                textError = phoneState.errorText,
                isError = phoneState.showError,
            ),
        )
    )
}

@Composable
private fun PasswordInputBlock(
    state: LoginUiState.PasswordState,
    onPasswordChanged: (TextFieldValue) -> Unit,
    onResetPasswordIconClick: () -> Unit,
    onPasswordIconClick: () -> Unit,
) {
    val localFocusManager = LocalFocusManager.current

    PasswordInput(
        state = PasswordInputState(
            mainState = PasswordInputState.MainState(
                textFieldValue = state.textFieldValue,
                placeholderText = state.placeholderText,
                onValueChange = { textFieldValue ->
                    onPasswordChanged(textFieldValue)
                },
                keyboardActions = KeyboardActions(
                    onDone = {
                        localFocusManager.clearFocus()
                    },
                ),
                keyboardImeAction = ImeAction.Done,
                isPasswordVisible = state.isPasswordVisible,
            ),
            tailState = PasswordInputState.TailState(
                onIconClick = onPasswordIconClick,
                onResetIconClick = onResetPasswordIconClick,
            ),
            errorState = PasswordInputState.ErrorState(
                textError = state.errorText,
                isError = state.showError,
            ),
        )
    )
}

@Preview
@Composable
private fun ContentPreview() {
    AppThemeContainer(viewScale = ViewScale.M) {
        Content(
            state = LoginUiState(
                phoneState = LoginUiState.PhoneState(
                    textFieldValue = TextFieldValue(),
                    placeholderText = "Введите номер телефона",
                    errorText = "",
                    showError = false,
                ),
                passwordState = LoginUiState.PasswordState(
                    textFieldValue = TextFieldValue(),
                    placeholderText = "Введите пароль",
                    errorText = "",
                    isPasswordVisible = false,
                    showError = false,
                ),
                confirmButtonsState = LoginUiState.ConfirmButtonState(
                    title = "Далее",
                    isEnabled = true,
                ),
            ),
            onPhoneChanged = {},
            onResetPhoneIconClick = {},
            onPasswordChanged = {},
            onResetPasswordIconClick = {},
            onPasswordIconClick = {},
            onConfirmButtonClick = {},
        )
    }
}
