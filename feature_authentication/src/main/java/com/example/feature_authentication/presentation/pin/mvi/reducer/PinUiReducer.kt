package com.example.feature_authentication.presentation.pin.mvi.reducer

import com.example.data_sdk_api.interactor.authentication.AuthenticationInteractor
import com.example.feature_authentication.biometric.model.BiometricAuthenticationResult
import com.example.feature_authentication.extension.convertToCharSequence
import com.example.feature_authentication.presentation.pin.mapper.CryptoObjectMapper
import com.example.feature_authentication.presentation.pin.mvi.model.PinDomainState
import com.example.feature_authentication.presentation.pin.mvi.model.PinEvent
import com.example.feature_authentication.presentation.pin.mvi.model.PinSideEffect
import com.example.feature_authentication.presentation.pin.mvi.model.PinUiCommand
import com.example.lib_ui.components.keyboard.model.PinKey
import com.example.lib_ui.components.keyboard.model.PinKeyType
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class PinUiReducer @Inject constructor(
    private val authenticationInteractor: AuthenticationInteractor,
    private val cryptoObjectMapper: CryptoObjectMapper,
) :
    Reducer<PinEvent.Ui, PinDomainState, PinSideEffect, PinUiCommand> {

    override fun update(
        state: PinDomainState,
        event: PinEvent.Ui,
    ): Update<PinDomainState, PinSideEffect, PinUiCommand> {
        return when (event) {
            is PinEvent.Ui.None -> Update.nothing()
            is PinEvent.Ui.OnBackPressed -> reduceOnBackPressed()
            is PinEvent.Ui.OnEnableBiometricsNeeded -> reduceOnEnableBiometricsNeeded(state)
            is PinEvent.Ui.OnKeyboardClick -> reduceOnKeyboardClick(state, event)
            is PinEvent.Ui.OnLogoutClick -> reduceOnLogoutClick()
            is PinEvent.Ui.OnBiometricsShowed -> reduceOnBiometricsShowed(state, event)
            is PinEvent.Ui.OnBiometricsResult -> reduceOnBiometricsResult(state, event)
        }
    }

    private fun reduceOnBackPressed(): Update<PinDomainState, PinSideEffect, PinUiCommand> {
        return Update.sideEffects(
            sideEffects = listOf(PinSideEffect.Ui.Back),
        )
    }

    private fun reduceOnLogoutClick(): Update<PinDomainState, PinSideEffect, PinUiCommand> {
        return Update.sideEffects(
            sideEffects = listOf(PinSideEffect.Ui.Logout),
        )
    }

    private fun reduceOnEnableBiometricsNeeded(state: PinDomainState): Update<PinDomainState, PinSideEffect, PinUiCommand> {
        return if (state.isBiometricsReady && state.cryptoObject != null) {
            Update.uiCommands(
                listOf(PinUiCommand.ShowBiometricsDialog(state.cryptoObject)),
            )
        } else {
            Update.nothing()
        }
    }

    private fun reduceOnBiometricsShowed(
        state: PinDomainState,
        event: PinEvent.Ui.OnBiometricsShowed,
    ): Update<PinDomainState, PinSideEffect, PinUiCommand> {
        return Update.state(
            state.copy(biometricPromptHandler = event.biometricPromptHandler)
        )
    }

    private fun reduceOnBiometricsResult(
        state: PinDomainState,
        event: PinEvent.Ui.OnBiometricsResult,
    ): Update<PinDomainState, PinSideEffect, PinUiCommand> {
        return when (val result = event.result) {
            is BiometricAuthenticationResult.Success -> {
                result.result.cryptoObject?.let { promptCryptoObject ->
                    return Update.stateWithSideEffects(
                        state = state.copy(
                            isLoading = true,
                        ),
                        sideEffects = listOf(
                            PinSideEffect.Domain.AuthenticationByBiometricNeeded(
                                cryptoObjectMapper.map(
                                    promptCryptoObject,
                                )
                            ),
                        ),
                    )
                }

                return Update.nothing()
            }

            is BiometricAuthenticationResult.Error -> Update.nothing()

            is BiometricAuthenticationResult.Failure -> Update.nothing()
        }
    }

    private fun reduceOnKeyboardClick(
        state: PinDomainState,
        event: PinEvent.Ui.OnKeyboardClick,
    ): Update<PinDomainState, PinSideEffect, PinUiCommand> {
        val key = event.key
        val keyType = key.type

        return when {
            keyType is PinKeyType.Digit && state.pin.count() < 3 -> {
                Update.state(
                    state.copy(
                        pin = state.pin.plus(keyType.value),
                        isError = false,
                    ),
                )
            }

            keyType is PinKeyType.Digit && state.pin.count() == 3 -> {
                val confirmedPin = state.pin.plus(keyType.value)

                return Update.stateWithSideEffects(
                    state = state.copy(
                        pin = confirmedPin,
                        isLoading = true,
                    ),
                    sideEffects = listOf(
                        PinSideEffect.Domain.AuthenticationByPinNeeded(confirmedPin.convertToCharSequence())
                    ),
                )
            }

            key == PinKey.BIOMETRICS -> {
                if (state.isBiometricsReady && state.cryptoObject != null) {
                    Update.uiCommands(
                        listOf(PinUiCommand.ShowBiometricsDialog(state.cryptoObject)),
                    )
                } else {
                    Update.nothing()
                }
            }

            key == PinKey.DELETE -> {
                val pin = if (state.pin.isEmpty()) {
                    state.pin
                } else {
                    state.pin.subList(fromIndex = 0, toIndex = state.pin.count().dec())
                }

                Update.state(
                    state.copy(
                        pin = pin,
                        isError = false,
                    ),
                )
            }

            else -> {
                Update.nothing()
            }
        }
    }
}
