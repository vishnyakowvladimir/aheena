package com.example.feature_authentication.presentation.pin.mvi.reducer

import com.example.data_sdk_api.interactor.authentication.AuthenticationInteractor
import com.example.feature_authentication.constants.REFRESH_TOKEN
import com.example.feature_authentication.extension.convertToCharSequence
import com.example.feature_authentication.presentation.pin.mvi.model.PinDomainState
import com.example.feature_authentication.presentation.pin.mvi.model.PinEvent
import com.example.feature_authentication.presentation.pin.mvi.model.PinSideEffect
import com.example.feature_authentication.presentation.pin.mvi.model.PinUiCommand
import com.example.lib_ui.components.keyboard.model.PinKeyType
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class PinUiReducer @Inject constructor(
    private val authenticationInteractor: AuthenticationInteractor,
) :
    Reducer<PinEvent.Ui, PinDomainState, PinSideEffect, PinUiCommand> {

    override fun update(
        state: PinDomainState,
        event: PinEvent.Ui,
    ): Update<PinDomainState, PinSideEffect, PinUiCommand> {
        return when (event) {
            is PinEvent.Ui.None -> Update.nothing()
            is PinEvent.Ui.OnBackPressed -> reduceOnBackPressed()
            is PinEvent.Ui.OnKeyboardClick -> reduceOnKeyboardClick(state, event)
        }
    }

    private fun reduceOnBackPressed(): Update<PinDomainState, PinSideEffect, PinUiCommand> {
        return Update.sideEffects(
            sideEffects = listOf(PinSideEffect.Ui.Back),
        )
    }

    private fun reduceOnKeyboardClick(
        state: PinDomainState,
        event: PinEvent.Ui.OnKeyboardClick,
    ): Update<PinDomainState, PinSideEffect, PinUiCommand> {
        val keyType = event.key.type

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


                val refreshToken = authenticationInteractor.getRefreshToken(confirmedPin.convertToCharSequence())
                val isError = refreshToken != REFRESH_TOKEN

                return if (isError) {
                    Update.state(
                        state.copy(
                            pin = emptyList(),
                            isError = true,
                        ),
                    )
                } else {
                    Update.stateWithSideEffects(
                        state = state.copy(
                            pin = confirmedPin,
                            isLoading = true,

                            ),
                        sideEffects = listOf(PinSideEffect.Ui.OpenMainScreen),
                    )
                }
            }

            keyType is PinKeyType.Icon -> {
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
