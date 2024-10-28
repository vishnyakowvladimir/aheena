package com.example.data_sdk_api.interactor.authentication

interface AuthenticationInteractor {

    fun saveRefreshToken(refreshToken: String, pinCode: String)
    fun clearRefreshToken()
}
