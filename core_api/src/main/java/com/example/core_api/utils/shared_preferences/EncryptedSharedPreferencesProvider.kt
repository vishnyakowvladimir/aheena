package com.example.core_api.utils.shared_preferences

import android.content.SharedPreferences

interface EncryptedSharedPreferencesProvider {

    fun getEncryptedSharedPreferences(isRecovery: Boolean): SharedPreferences
}
