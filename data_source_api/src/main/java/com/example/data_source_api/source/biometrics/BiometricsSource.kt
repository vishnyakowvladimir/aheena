package com.example.data_source_api.source.biometrics

interface BiometricsSource {

    fun saveEnabledBiometricsFlag(flag: Boolean)
    fun isBiometricsEnabled(): Boolean
}
