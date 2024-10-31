package com.example.data_source_api.repository.biometrics

interface BiometricsRepository {

    fun saveEnabledBiometricsFlag(flag: Boolean)
    fun isBiometricsEnabled(): Boolean
}
