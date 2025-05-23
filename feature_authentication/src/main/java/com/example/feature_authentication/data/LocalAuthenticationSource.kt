package com.example.feature_authentication.data

import com.example.core_api.di.scope.FeatureScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@FeatureScope
internal class LocalAuthenticationSource @Inject constructor() {

    private val refreshTokenStateFlow = MutableStateFlow<CharSequence>("")
    private val pinCodeStateFlow = MutableStateFlow<CharSequence>("")

    fun saveRefreshToken(refreshToken: CharSequence) {
        refreshTokenStateFlow.tryEmit(refreshToken)
    }

    fun getRefreshToken(): Flow<CharSequence> {
        return refreshTokenStateFlow
    }

    fun savePinCode(pinCode: CharSequence) {
        pinCodeStateFlow.tryEmit(pinCode)
    }

    fun getPinCode(): Flow<CharSequence> {
        return pinCodeStateFlow
    }
}
