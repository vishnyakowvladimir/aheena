package com.example.data_sdk_api.interactor.authentication

interface AuthenticationInteractor {

    fun saveRefreshToken(refreshToken: CharSequence, pinCode: CharSequence)
    fun clearRefreshToken()
}
