package com.example.feature_authentication.presentation.create_pin.mvi.reducer

import com.example.feature_authentication.presentation.create_pin.mvi.model.CreatePinDomainState
import com.example.feature_authentication.presentation.create_pin.mvi.model.CreatePinEvent
import com.example.feature_authentication.presentation.create_pin.mvi.model.CreatePinSideEffect
import com.example.feature_authentication.presentation.create_pin.mvi.model.CreatePinUiCommand
import com.example.lib_ui.components.keyboard.model.PinKeyType
import com.example.mvi.Reducer
import com.example.mvi.model.Update
import javax.inject.Inject

internal class CreatePinUiReducer @Inject constructor() :
    Reducer<CreatePinEvent.Ui, CreatePinDomainState, CreatePinSideEffect, CreatePinUiCommand> {

    override fun update(
        state: CreatePinDomainState,
        event: CreatePinEvent.Ui,
    ): Update<CreatePinDomainState, CreatePinSideEffect, CreatePinUiCommand> {
        return when (event) {
            is CreatePinEvent.Ui.OnBackPressed -> reduceOnBackPressed()
            is CreatePinEvent.Ui.OnKeyboardClick -> reduceOnKeyboardClick(state, event)
            is CreatePinEvent.Ui.OnDelayBeforeChangeMode -> reduceOnDelayBeforeChangeMode(state)
        }
    }

    private fun reduceOnBackPressed(): Update<CreatePinDomainState, CreatePinSideEffect, CreatePinUiCommand> {
        return Update.sideEffects(
            sideEffects = listOf(CreatePinSideEffect.Ui.Back),
        )
    }

    private fun reduceOnKeyboardClick(
        state: CreatePinDomainState,
        event: CreatePinEvent.Ui.OnKeyboardClick,
    ): Update<CreatePinDomainState, CreatePinSideEffect, CreatePinUiCommand> {
        if (state.isLoading) {
            return Update.nothing()
        }

        val createPinState = state.createPinState
        val confirmPinState = state.confirmPinState

        val keyType = event.key.type

        return when (state.mode) {
            CreatePinDomainState.Mode.CREATE -> {
                when {
                    keyType is PinKeyType.Digit && createPinState.pin.count() < 3 -> {
                        val updatedCreatePinState = createPinState.copy(
                            pin = createPinState.pin.plus(keyType.value)
                        )

                        Update.state(
                            state.copy(
                                createPinState = updatedCreatePinState,
                                isError = false,
                            )
                        )
                    }

                    keyType is PinKeyType.Digit && createPinState.pin.count() == 3 -> {
                        val updatedCreatePinState = createPinState.copy(
                            pin = createPinState.pin.plus(keyType.value)
                        )

                        Update.stateWithSideEffects(
                            state = state.copy(
                                createPinState = updatedCreatePinState,
                                isError = false,
                            ),
                            sideEffects = listOf(CreatePinSideEffect.Ui.DelayBeforeChangeMode),
                        )
                    }

                    keyType is PinKeyType.Icon -> {
                        val pin = if (createPinState.pin.isEmpty()) {
                            createPinState.pin
                        } else {
                            createPinState.pin.subList(fromIndex = 0, toIndex = createPinState.pin.count().dec())
                        }

                        val updatedCreatePinState = createPinState.copy(
                            pin = pin
                        )

                        Update.state(
                            state.copy(
                                createPinState = updatedCreatePinState,
                                isError = false,
                            ),
                        )
                    }

                    else -> {
                        Update.nothing()
                    }
                }
            }

            CreatePinDomainState.Mode.CONFIRM -> {
                when {
                    keyType is PinKeyType.Digit && confirmPinState.pin.count() < 3 -> {
                        val updatedConfirmPinState = confirmPinState.copy(
                            pin = confirmPinState.pin.plus(keyType.value)
                        )

                        Update.state(
                            state.copy(
                                confirmPinState = updatedConfirmPinState,
                                isError = false,
                            ),
                        )
                    }

                    keyType is PinKeyType.Digit && confirmPinState.pin.count() == 3 -> {
                        val confirmedPin = confirmPinState.pin.plus(keyType.value)

                        val isError = createPinState.pin != confirmedPin

                        return if (isError) {
                            Update.state(
                                state.copy(
                                    createPinState = createPinState.copy(
                                        pin = emptyList(),
                                    ),
                                    confirmPinState = confirmPinState.copy(
                                        pin = emptyList()
                                    ),
                                    mode = CreatePinDomainState.Mode.CREATE,
                                    isError = true,
                                ),
                            )
                        } else {
                            Update.stateWithSideEffects(
                                state = state.copy(
                                    confirmPinState = confirmPinState.copy(
                                        pin = confirmedPin,
                                    ),
                                    isLoading = true,

                                    ),
                                sideEffects = listOf(
                                    CreatePinSideEffect.Domain.SaveRefreshToken(
                                        pinCode = confirmedPin,
                                    ),
                                ),
                            )
                        }
                    }

                    keyType is PinKeyType.Icon -> {
                        val pin = if (confirmPinState.pin.isEmpty()) {
                            confirmPinState.pin
                        } else {
                            confirmPinState.pin.subList(fromIndex = 0, toIndex = confirmPinState.pin.count().dec())
                        }

                        val updatedConfirmPinState = confirmPinState.copy(
                            pin = pin,
                        )

                        Update.state(
                            state.copy(
                                confirmPinState = updatedConfirmPinState,
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
    }

    private fun reduceOnDelayBeforeChangeMode(
        state: CreatePinDomainState,
    ): Update<CreatePinDomainState, CreatePinSideEffect, CreatePinUiCommand> {
        return Update.state(
            state = state.copy(
                mode = CreatePinDomainState.Mode.CONFIRM,
            )
        )
    }
}
