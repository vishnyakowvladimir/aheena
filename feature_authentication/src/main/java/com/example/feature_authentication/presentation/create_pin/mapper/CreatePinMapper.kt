package com.example.feature_authentication.presentation.create_pin.mapper

import com.example.core.utils.string_provider.StringProvider
import com.example.feature_authentication.R
import com.example.feature_authentication.presentation.create_pin.model.CreatePinUiState
import com.example.feature_authentication.presentation.create_pin.mvi.model.CreatePinDomainState
import com.example.lib_ui.components.pincode.model.PinCodeFieldItemIndex
import com.example.lib_ui.components.pincode.model.PinCodeFieldState
import com.example.lib_ui.components.pincode.model.PinCodeFieldType
import javax.inject.Inject

internal class CreatePinMapper @Inject constructor(
    private val stringProvider: StringProvider,
) {
    fun map(domainState: CreatePinDomainState): CreatePinUiState {
        return CreatePinUiState(
            pinCodeFieldState = mapPinCodeFieldState(domainState),
        )
    }

    private fun mapPinCodeFieldState(domainState: CreatePinDomainState): PinCodeFieldState {
        val title = if (domainState.mode == CreatePinDomainState.Mode.CREATE) {
            stringProvider.getString(R.string.authentication_create_pin_field_title_create)
        } else {
            stringProvider.getString(R.string.authentication_create_pin_field_title_confirm)
        }

        val type = when {
            domainState.isError -> {
                PinCodeFieldType.Error(
                    errorText = stringProvider.getString(R.string.authentication_create_pin_error)
                )
            }
            else -> {
                val selectedIndex = if (domainState.mode == CreatePinDomainState.Mode.CREATE) {
                    domainState.createPinState.pin.lastIndex
                } else {
                    domainState.confirmPinState.pin.lastIndex
                }

                PinCodeFieldType.Default(
                    selectedIndex = PinCodeFieldItemIndex.getPinCodeFieldItemIndex(selectedIndex),
                )
            }
        }

        return PinCodeFieldState(
            title = title,
            maxIndex = PinCodeFieldItemIndex.THREE,
            type = type,
        )
    }
}
