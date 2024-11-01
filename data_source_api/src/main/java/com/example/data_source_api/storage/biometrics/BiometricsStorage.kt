package com.example.data_source_api.storage.biometrics

interface BiometricsStorage {

    fun saveEnabledBiometricsFlag(flag: Boolean)
    fun isBiometricsEnabled(): Boolean
}
