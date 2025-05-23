package com.example.data_source_impl.repository.biometrics

import com.example.data_source_api.repository.biometrics.BiometricsRepository
import com.example.data_source_api.storage.biometrics.BiometricsStorage
import javax.inject.Inject

class BiometricsRepositoryImpl @Inject constructor(
    private val biometricsSource: BiometricsStorage,
) : BiometricsRepository {
    override fun saveEnabledBiometricsFlag(flag: Boolean) {
        biometricsSource.saveEnabledBiometricsFlag(flag)
    }

    override fun isBiometricsEnabled(): Boolean {
        return biometricsSource.isBiometricsEnabled()
    }
}
