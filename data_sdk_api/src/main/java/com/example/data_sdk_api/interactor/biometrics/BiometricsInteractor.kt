package com.example.data_sdk_api.interactor.biometrics

interface BiometricsInteractor {

    fun saveEnabledBiometricsFlag(flag: Boolean)
    fun isBiometricsEnabled(): Boolean
}
