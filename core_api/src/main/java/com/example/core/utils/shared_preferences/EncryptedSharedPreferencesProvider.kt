package com.example.core.utils.shared_preferences

import android.content.SharedPreferences

interface EncryptedSharedPreferencesProvider {

    fun getEncryptedSharedPreferences(isRecovery: Boolean): SharedPreferences
}
