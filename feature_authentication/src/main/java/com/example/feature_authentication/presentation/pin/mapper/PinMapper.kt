package com.example.feature_authentication.presentation.pin.mapper

import com.example.core.utils.string_provider.StringProvider
import com.example.feature_authentication.R
import com.example.feature_authentication.presentation.pin.model.PinUiState
import com.example.feature_authentication.presentation.pin.mvi.model.PinDomainState
import com.example.lib_ui.components.pincode.model.PinCodeFieldItemIndex
import com.example.lib_ui.components.pincode.model.PinCodeFieldState
import com.example.lib_ui.components.pincode.model.PinCodeFieldType
import javax.inject.Inject

internal class PinMapper @Inject constructor(
    private val stringProvider: StringProvider,
) {
    fun map(domainState: PinDomainState): PinUiState {
        return PinUiState(
            pinCodeFieldState = mapPinCodeFieldState(domainState),
            withBiometrics = domainState.isBiometricsReady,
        )
    }

    private fun mapPinCodeFieldState(domainState: PinDomainState): PinCodeFieldState {
        val type = when {
            domainState.isError -> {
                PinCodeFieldType.Error(
                    errorText = stringProvider.getString(R.string.authentication_pin_error)
                )
            }

            domainState.isLoading -> {
                PinCodeFieldType.Loading
            }

            else -> {
                PinCodeFieldType.Default(
                    selectedIndex = PinCodeFieldItemIndex.getPinCodeFieldItemIndex(domainState.pin.lastIndex),
                )
            }
        }

        return PinCodeFieldState(
            title = stringProvider.getString(R.string.authentication_pin_field_title),
            maxIndex = PinCodeFieldItemIndex.THREE,
            type = type,
        )
    }
}
