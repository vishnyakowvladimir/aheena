package com.example.core_impl.utils.shared_preferences

import android.content.Context
import androidx.preference.PreferenceManager
import com.example.core_api.utils.shared_preferences.AndroidPreferencesProvider
import com.example.core_api.utils.shared_preferences.EncryptedSharedPreferencesProvider
import javax.inject.Inject

class AndroidPreferencesProviderImpl @Inject constructor(
    context: Context,
    encryptedSharedPreferencesProvider: EncryptedSharedPreferencesProvider
) : AndroidPreferencesProvider {

    override val cryptoPrefs by lazy {
        encryptedSharedPreferencesProvider.getEncryptedSharedPreferences(false)
    }

    override val prefs by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }
}
