package com.example.feature_authentication.presentation.phone_and_password

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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lib_ui.components.button.AppButton
import com.example.lib_ui.components.button.AppButtonState
import com.example.lib_ui.components.input.password_input.PasswordInput
import com.example.lib_ui.components.input.password_input.PasswordInputState
import com.example.lib_ui.components.input.phone_input.PhoneInput
import com.example.lib_ui.components.input.phone_input.PhoneInputState
import com.example.lib_ui.theme.AppTheme
import com.example.lib_ui.theme.AppThemeContainer
import com.example.lib_ui.theme.typography.ViewScale
import com.example.lib_ui.utils.SetSystemBarsColor

@Composable
internal fun PhoneAndPasswordScreen(
    viewModel: PhoneAndPasswordViewModel,
) {
    SetSystemBarsColor(
        statusBarColor = AppTheme.palette.background.primary,
    )

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
        containerColor = AppTheme.palette.background.primary,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Content()
        }
    }
}

@Composable
private fun Content() {
    val localFocusManager = LocalFocusManager.current

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
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                PhoneInput(
                    state = PhoneInputState(
                        mainState = PhoneInputState.MainState(
                            textFieldValue = TextFieldValue(annotatedString = AnnotatedString(text = "")),
                            placeholderText = "Введите номер телефона",
                            onValueChange = {},
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    localFocusManager.moveFocus(FocusDirection.Down)
                                },
                            ),
                            imeAction = ImeAction.Next,
                        ),
                        tailState = PhoneInputState.TailState(
                            onResetIconClick = {},
                        ),
                        errorState = PhoneInputState.ErrorState(
                            textError = "",
                            isError = false,
                        ),
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                PasswordInput(
                    state = PasswordInputState(
                        mainState = PasswordInputState.MainState(
                            textFieldValue = TextFieldValue(annotatedString = AnnotatedString(text = "")),
                            placeholderText = "Введите пароль",
                            onValueChange = {},
                            maxLength = 15,
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    localFocusManager.clearFocus()
                                },
                            ),
                            keyboardImeAction = ImeAction.Done,
                            isPasswordVisible = false,
                        ),
                        tailState = PasswordInputState.TailState(
                            onIconClick = {},
                            onResetIconClick = {},
                        ),
                        errorState = PasswordInputState.ErrorState(
                            textError = "Ошибка",
                            isError = false,
                        ),
                    )
                )
            }
        }

        AppButton(
            modifier = Modifier.fillMaxWidth(),
            state = AppButtonState(
                title = "Button",
            ),
            onClick = {},
        )
    }
}

@Preview
@Composable
private fun ContentPreview() {
    AppThemeContainer(viewScale = ViewScale.M) {
        Content()
    }
}
