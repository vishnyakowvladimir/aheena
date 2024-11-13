package com.example.feature_authentication.presentation.pin

import androidx.biometric.BiometricPrompt
import androidx.lifecycle.viewModelScope
import com.example.core_api.presentation.base.BaseViewModel
import com.example.core_api.utils.extension.mapData
import com.example.core_api.utils.string_provider.StringProvider
import com.example.feature_authentication.R
import com.example.feature_authentication.biometric.BiometricController
import com.example.feature_authentication.biometric.model.BiometricAuthenticators
import com.example.feature_authentication.presentation.pin.mapper.CryptoObjectMapper
import com.example.feature_authentication.presentation.pin.mapper.PinMapper
import com.example.feature_authentication.presentation.pin.mvi.handler.PinSideEffectHandler
import com.example.feature_authentication.presentation.pin.mvi.model.PinEvent
import com.example.feature_authentication.presentation.pin.mvi.model.PinUiCommand
import com.example.feature_authentication.presentation.pin.mvi.reducer.PinReducer
import com.example.mvi.MviStore
import com.example.mvi.StateMachine
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class PinViewModel @Inject constructor(
    private val mapper: PinMapper,
    private val sideEffectHandler: PinSideEffectHandler,
    private val reducer: PinReducer,
    private val biometricController: BiometricController,
    private val cryptoObjectMapper: CryptoObjectMapper,
    private val stringProvider: StringProvider,
) : BaseViewModel() {
    private val uiEvent = MutableSharedFlow<PinEvent>(replay = Int.MAX_VALUE)

    private val _uiState = MutableStateFlow(reducer.getInitialState())
    val uiState = _uiState.asStateFlow().mapData(
        coroutineScope = viewModelScope,
        mapper = { domainState -> mapper.map(domainState) },
    )

    init {
        createMvi()
        viewModelScope.launch {
            onEvent(PinEvent.Ui.OnEnableBiometricsNeeded)
        }
    }

    fun onEvent(event: PinEvent) {
        viewModelScope.launch { uiEvent.emit(event) }
    }

    private fun createMvi() {
        val mviStore = MviStore(
            stateMachine = StateMachine(
                reducer,
                reducer.getInitialState(),
            ),
            sideEffectHandler = sideEffectHandler,
        )
        mviStore.start(
            coroutineScope = viewModelScope,
            actionState = { state ->
                _uiState.update { state }
            },
            actionUiCommand = { uiCommand ->
                handleUiCommand(uiCommand)
            },
        )

        uiEvent
            .onEach(mviStore::onEvent)
            .launchIn(viewModelScope)
    }

    private fun handleUiCommand(uiCommand: PinUiCommand) {
        when (uiCommand) {
            is PinUiCommand.ShowBiometricsDialog -> handleUiCommandShowBiometricsDialog(uiCommand)
        }
    }

    private fun handleUiCommandShowBiometricsDialog(uiCommand: PinUiCommand.ShowBiometricsDialog) {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(stringProvider.getString(R.string.authentication_pin_biometric_title))
            .setDescription(stringProvider.getString(R.string.authentication_pin_biometric_description))
            .setNegativeButtonText(stringProvider.getString(R.string.authentication_pin_biometric_cancel))
            .setAllowedAuthenticators(BiometricAuthenticators.STRONG.value)
            .build()

        val biometricPromptHandler = biometricController.showBiometricsWindow(
            promptInfo = promptInfo,
            cryptoObject = cryptoObjectMapper.map(uiCommand.cryptoObject),
            resultAction = { result ->
                onEvent(PinEvent.Ui.OnBiometricsResult(result))
            },
        )

        onEvent(PinEvent.Ui.OnBiometricsShowed(biometricPromptHandler))
    }
}
