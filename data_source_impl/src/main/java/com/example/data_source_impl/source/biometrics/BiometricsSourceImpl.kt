package com.example.data_source_impl.source.biometrics

import androidx.core.content.edit
import com.example.core.utils.shared_preferences.AndroidPreferencesProvider
import com.example.data_source_api.source.biometrics.BiometricsSource
import javax.inject.Inject

private const val BIOMETRICS_FLAG_KEY = "biometrics_flag_key"

class BiometricsSourceImpl @Inject constructor(
    private val preferencesProvider: AndroidPreferencesProvider,
) : BiometricsSource {

    override fun saveEnabledBiometricsFlag(flag: Boolean) {
        preferencesProvider.prefs.edit {
            putBoolean(BIOMETRICS_FLAG_KEY, flag)
        }
    }

    override fun isBiometricsEnabled(): Boolean {
        return preferencesProvider.prefs.getBoolean(BIOMETRICS_FLAG_KEY, false)
    }
}
