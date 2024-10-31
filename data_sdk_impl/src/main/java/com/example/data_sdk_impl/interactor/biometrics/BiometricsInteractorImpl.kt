package com.example.data_sdk_impl.interactor.biometrics

import com.example.data_sdk_api.interactor.biometrics.BiometricsInteractor
import com.example.data_source_api.repository.biometrics.BiometricsRepository
import javax.inject.Inject

class BiometricsInteractorImpl @Inject constructor(
    private val biometricsRepository: BiometricsRepository
) : BiometricsInteractor {

    override fun saveEnabledBiometricsFlag(flag: Boolean) {
        biometricsRepository.saveEnabledBiometricsFlag(flag)
    }

    override fun isBiometricsEnabled(): Boolean {
        return biometricsRepository.isBiometricsEnabled()
    }
}
