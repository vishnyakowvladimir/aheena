package com.example.data_source_impl.storage.biometrics

import androidx.core.content.edit
import com.example.core_api.utils.shared_preferences.AndroidPreferencesProvider
import com.example.data_source_api.storage.biometrics.BiometricsStorage
import javax.inject.Inject

private const val BIOMETRICS_FLAG_KEY = "biometrics_flag_key"

class BiometricsStorageImpl @Inject constructor(
    private val preferencesProvider: AndroidPreferencesProvider,
) : BiometricsStorage {

    override fun saveEnabledBiometricsFlag(flag: Boolean) {
        preferencesProvider.prefs.edit {
            putBoolean(BIOMETRICS_FLAG_KEY, flag)
        }
    }

    override fun isBiometricsEnabled(): Boolean {
        return preferencesProvider.prefs.getBoolean(BIOMETRICS_FLAG_KEY, false)
    }
}
